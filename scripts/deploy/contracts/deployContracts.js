/* eslint-disable no-console */
/* eslint-disable-next-line security/detect-child-process */
const { execSync } = require('child_process')

const pkg = require('../../../package.json')

const zosInit = require('./zos/init')
const zosRegisterContracts = require('./zos/registerContracts')
const initializeContracts = require('./initializeContracts')
const zosSetAdmin = require('./zos/setAdmin')
const exportArtifacts = require('./artifacts/exportArtifacts')

/*
 *-----------------------------------------------------------------------
 * Script configuration
 * -----------------------------------------------------------------------
 * Config variables for initializers
 */
// load NETWORK from environment
const NETWORK = process.env.NETWORK || 'development'
// load current version from package
const VERSION = `v${pkg.version}`

// List of contracts
const contractNames = [
    'ConditionStoreManager',
    'TemplateStoreManager',
    'AgreementStoreManager',
    'SignCondition',
    'HashLockCondition',
    'LockRewardCondition',
    'AccessSecretStoreCondition',
    'EscrowReward',
    'EscrowAccessSecretStoreTemplate',
    'OceanToken',
    'Dispenser',
    'DIDRegistry'
]

async function deployContracts(
    web3,
    artifacts,
    contracts,
    verbose = true
) {
    contracts = !contracts || contracts.length === 0 ? contractNames : contracts

    if (contracts.find((contract) => contract.indexOf(':') > -1)) {
        throw new Error(`Bad input please use 'ContractName'`)
    }

    execSync('rm -f ./zos.*', { stdio: 'ignore' })

    if (verbose) {
        console.log(`Deploying contracts: '${contracts.join(', ')}'`)
    }

    const roles = await zosInit(
        web3,
        pkg.name,
        NETWORK,
        VERSION,
        verbose
    )

    await zosRegisterContracts(
        contracts,
        false,
        verbose
    )

    const addressBook = await initializeContracts(
        artifacts,
        contracts,
        roles,
        verbose
    )

    if (verbose) {
        console.log(
            `Contracts deployed to the proxies: \n${JSON.stringify(addressBook, null, 2)}`
        )
    }

    await zosSetAdmin(
        contracts,
        roles,
        verbose
    )

    await exportArtifacts(
        NETWORK,
        VERSION,
        verbose
    )

    return addressBook
}

module.exports = deployContracts
