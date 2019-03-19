/* eslint-disable no-console */
const fs = require('fs')
const pkg = require('../../../package.json')

const zosCleanup = require('./zos/setup/cleanup')
const zosInit = require('./zos/setup/init')

const zosRegisterContracts = require('./zos/contracts/registerContracts')
const zosRequestContractUpgrade = require('./zos/contracts/requestContractUpgrades')

const updateArtifact = require('./artifacts/updateArtifact')
const loadWallet = require('../wallet/loadWallet')

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

const artifactsDir = `${__dirname}/../../../artifacts/`

const contractNames = require('./contracts.json')

async function upgradeContracts(
    web3,
    contracts = [],
    verbose = true
) {
    contracts = !contracts || contracts.length === 0 ? contractNames : contracts

    if (verbose) {
        console.log(`Upgrading contracts: '${contracts.join(', ')}'`)
    }

    await zosCleanup(
        web3,
        false,
        verbose
    )

    // init zos
    const roles = await zosInit(
        web3,
        pkg.name,
        NETWORK,
        VERSION,
        false,
        verbose
    )

    // register contract upgrades in zos, force it
    await zosRegisterContracts(
        contracts,
        true,
        verbose
    )

    const upgraderWallet = await loadWallet(
        web3,
        'upgrader',
        verbose
    )

    const taskBook = {}

    for (const contractName of contracts) {
        const [newContractName, oldContractName] = contractName.indexOf(':') > -1 ? contractName.split(':') : [contractName, contractName]
        const networkId = await web3.eth.net.getId()

        /* eslint-disable-next-line security/detect-non-literal-fs-filename */
        const artifactString = fs.readFileSync(
            `${artifactsDir}${oldContractName}.${NETWORK.toLowerCase()}.json`,
            'utf8'
        ).toString()

        const artifact = JSON.parse(artifactString)

        // get proxy address of current implementation
        const { address } = artifact

        taskBook[oldContractName] = await zosRequestContractUpgrade(
            oldContractName,
            newContractName,
            address,
            upgraderWallet,
            roles,
            networkId,
            verbose
        )

        updateArtifact(
            oldContractName,
            newContractName,
            VERSION,
            networkId,
            verbose
        )
    }

    if (verbose) {
        console.log(
            `Tasks created: \n${JSON.stringify(taskBook, null, 2)}\nplease approve them in the wallet: '${upgraderWallet.address}'`
        )
    }

    return taskBook
}

module.exports = upgradeContracts
