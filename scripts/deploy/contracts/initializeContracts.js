/* eslint-disable no-console */
const zosCreate = require('./zos/create')

async function initializeContracts(
    artifacts,
    contracts,
    roles,
    verbose = true
) {
    // Deploy all implementations in the specified network.
    // NOTE: Creates another zos.<network_name>.json file, specific to the network used,
    // which keeps track of deployed addresses, etc.

    // Here we run initialize which replace contract constructors
    // Since each contract initialize function could be different we can not use a loop
    // NOTE: A dapp could now use the address of the proxy specified in zos.<network_name>.json
    // instance=MyContract.at(proxyAddress)

    const addressBook = {}

    // v0.7
    if (contracts.indexOf('DIDRegistry') > -1) {
        addressBook['DIDRegistry'] = zosCreate(
            'DIDRegistry',
            [roles.ownerWallet],
            verbose
        )
    }

    if (contracts.indexOf('OceanToken') > -1) {
        addressBook['OceanToken'] = zosCreate(
            'OceanToken',
            [
                roles.ownerWallet,
                roles.initialMinter
            ],
            verbose
        )
    }

    if (addressBook['OceanToken']) {
        if (contracts.indexOf('Dispenser') > -1) {
            addressBook['Dispenser'] = zosCreate(
                'Dispenser',
                [
                    addressBook['OceanToken'],
                    roles.ownerWallet
                ],
                verbose
            )
        }
    }

    if (contracts.indexOf('ConditionStoreManager') > -1) {
        addressBook['ConditionStoreManager'] = zosCreate(
            'ConditionStoreManager',
            null,
            verbose
        )
    }

    if (contracts.indexOf('TemplateStoreManager') > -1) {
        addressBook['TemplateStoreManager'] = zosCreate(
            'TemplateStoreManager',
            [roles.ownerWallet],
            verbose
        )
    }

    if (addressBook['ConditionStoreManager']) {
        if (contracts.indexOf('SignCondition') > -1) {
            addressBook['SignCondition'] = zosCreate(
                'SignCondition',
                [
                    roles.ownerWallet,
                    addressBook['ConditionStoreManager']
                ],
                verbose
            )
        }
        if (contracts.indexOf('HashLockCondition') > -1) {
            addressBook['HashLockCondition'] = zosCreate(
                'HashLockCondition',
                [
                    roles.ownerWallet,
                    addressBook['ConditionStoreManager']
                ],
                verbose
            )
        }
    }

    if (addressBook['ConditionStoreManager'] &&
        addressBook['TemplateStoreManager'] &&
        addressBook['DIDRegistry']) {
        if (contracts.indexOf('AgreementStoreManager') > -1) {
            addressBook['AgreementStoreManager'] = zosCreate(
                'AgreementStoreManager',
                [
                    roles.ownerWallet,
                    addressBook['ConditionStoreManager'],
                    addressBook['TemplateStoreManager'],
                    addressBook['DIDRegistry']
                ],
                verbose
            )
        }
    }

    if (addressBook['ConditionStoreManager'] &&
        addressBook['OceanToken']) {
        if (contracts.indexOf('LockRewardCondition') > -1) {
            addressBook['LockRewardCondition'] = zosCreate(
                'LockRewardCondition',
                [
                    roles.ownerWallet,
                    addressBook['ConditionStoreManager'],
                    addressBook['OceanToken']
                ],
                verbose
            )
        }
        if (contracts.indexOf('EscrowReward') > -1) {
            addressBook['EscrowReward'] = zosCreate(
                'EscrowReward',
                [
                    roles.ownerWallet,
                    addressBook['ConditionStoreManager'],
                    addressBook['OceanToken']
                ],
                verbose
            )
        }
    }

    if (addressBook['ConditionStoreManager'] &&
        addressBook['AgreementStoreManager']) {
        if (contracts.indexOf('AccessSecretStoreCondition') > -1) {
            addressBook['AccessSecretStoreCondition'] = zosCreate(
                'AccessSecretStoreCondition',
                [
                    roles.ownerWallet,
                    addressBook['ConditionStoreManager'],
                    addressBook['AgreementStoreManager']
                ],
                verbose
            )
        }
    }

    if (addressBook['AgreementStoreManager'] &&
        addressBook['DIDRegistry'] &&
        addressBook['AccessSecretStoreCondition'] &&
        addressBook['LockRewardCondition'] &&
        addressBook['EscrowReward']) {
        if (contracts.indexOf('EscrowAccessSecretStoreTemplate') > -1) {
            addressBook['EscrowAccessSecretStoreTemplate'] = zosCreate(
                'EscrowAccessSecretStoreTemplate',
                [
                    roles.ownerWallet,
                    addressBook['AgreementStoreManager'],
                    addressBook['DIDRegistry'],
                    addressBook['AccessSecretStoreCondition'],
                    addressBook['LockRewardCondition'],
                    addressBook['EscrowReward']
                ],
                verbose
            )
        }
    }

    /*
     * -----------------------------------------------------------------------
     * setup deployed contracts
     * -----------------------------------------------------------------------
     */

    // TODO: @sebastian - please check
    if (addressBook['ConditionStoreManager'] &&
        addressBook['AgreementStoreManager']) {
        const ConditionStoreManager = artifacts.require('ConditionStoreManager')
        const conditionStoreManager = await ConditionStoreManager.at(addressBook['ConditionStoreManager'])
        await conditionStoreManager.initialize(
            roles.ownerWallet,
            { from: roles.upgrader })
        //TODO: delegate createRole to addressBook['AgreementStoreManager']
    }

    if (addressBook['OceanToken']) {
        const OceanToken = artifacts.require('OceanToken')
        const oceanToken = await OceanToken.at(addressBook['OceanToken'])

        if (addressBook['Dispenser']) {
            if (verbose) {
                console.log(`adding dispenser as a minter ${addressBook['Dispenser']} from ${roles.initialMinter}`)
            }

            await oceanToken.addMinter(
                addressBook['Dispenser'],
                { from: roles.initialMinter })
        }

        if (verbose) {
            console.log(`Renouncing initialMinter as a minter from ${roles.initialMinter}`)
        }
        await oceanToken.renounceMinter({ from: roles.initialMinter })
    }

    return addressBook
}

module.exports = initializeContracts
