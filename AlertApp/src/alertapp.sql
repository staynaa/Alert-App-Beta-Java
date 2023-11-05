CREATE DATABASE SMSAlertDB; -- create database

-- userinfo stores log in info for user
CREATE TABLE IF NOT EXISTS userinfo( 
    usid INT PRIMARY KEY, --user's id number 
    fname VARCHAR(100), -- user's first name
    lname VARCHAR(100), -- user's last name
    email VARCHAR(320), --64 user name 1 for @ 255 email url to sign up
    userphone VARCHAR(12) -- user's number +10000000000 no dash no space
)

-- emergencycont stores emergency contact numbers the user registers.
CREATE TABLE IF NOT EXISTS emergencycont(
    cid INT, -- the contact's id
    usid INT, -- the id of the user who registered the contact
    cfname VARCHAR(100), -- contact's first name
    clname VARCHAR(100), -- contact's last name
    contphone VARCHAR(12), -- the contact number that will get the SMS
    active BIT(1) -- 0(false) or 1(true) if the contact number is still active for the user
)