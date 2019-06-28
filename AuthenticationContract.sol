pragma solidity ^0.5.7;

contract Users {
    address owner;

    struct UserDto {
        string name;

        string family;

        string email;

        string date;

        string sex;
    }

    mapping (string => UserDto) private users;

    string[] public userTemplates;

    constructor() public {
        owner = msg.sender;
    }

    modifier onlyOwner {
        require(msg.sender == owner);
        _;
    }

    function getUser(string memory key) public view returns(string memory name, string memory family, string memory email,string memory date,string memory sex) {
        return (users[key].name, users[key].family, users[key].email,users[key].date,users[key].sex);
    }

    function addUser(string memory name, string memory family, string memory email, string memory dob, string memory sex, string memory template) public {
        UserDto memory newUser = UserDto(name, family, email, dob, sex);
        userTemplates.push(template);
        users[template] = newUser;
    }

    function editUser(string memory name, string memory family, string memory email, string memory dob, string memory sex, string memory template) public{
        users[template].name = name;
        users[template].family = family;
        users[template].email = email;
        users[template].date = dob;
        users[template].sex = sex;
    }

    function getTemplatesSize() public view returns(uint) {
        return userTemplates.length;
    }
    
}
