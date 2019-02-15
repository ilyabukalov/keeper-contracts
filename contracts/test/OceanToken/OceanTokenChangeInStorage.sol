pragma solidity 0.5.3;

// Contain upgraded version of the contracts for test
import '../../OceanToken.sol';


contract OceanTokenExtraFunctionality is OceanToken {
    using SafeMath for uint256;
    uint256 mintCount;
}
