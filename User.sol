pragma solidity ^0.4.17;

contract Users {
    address owner;
    
    struct UserDto {
        string name;
        
        string date;
        
        string sex;
    }
    
    mapping (string => UserDto) private users;
    
    string[] public userTemplates;
    
    function Users() public {
        owner = msg.sender;
    }

    modifier onlyOwner {
        require(msg.sender == owner);
        _;
    }
    
    function getUser(string key) public constant returns(string memory name,string memory date,string memory sex) {
        return (users[key].name,users[key].date,users[key].sex);
    }
    
    function addUser(string name, string dob, string sex, string template) public {
        UserDto memory newUser = UserDto(name, dob, sex);
        userTemplates.push(template);
        users[template] = newUser;
    }
    
    function getTemplatesSize() public constant returns(uint) {
        return userTemplates.length;
    }
    
}
