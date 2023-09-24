#! python3
# blackboardEzLogIn.py - automatically opens edge and logs into blackboard using UTEP SSO

from selenium import webdriver
from pathlib import Path
from selenium.webdriver.edge.service import Service

# Save login info to a file
try:
    loginData = open(Path.home()/'loginData.txt','r')
except FileNotFoundError:
    loginData = open(Path.home()/'loginData.txt', 'w')
    print('Please enter your username: ')
    username = input()
    print('Please enter your password: ')
    password = input()
    print('Are you sure these are correct? "Y" for yes, "N" for no: ')
    valid = input()
    while valid.upper() != 'Y':
        print('Please enter your username: ')
        username = input()
        print('Please enter your password: ')
        password = input()
        print('Are you sure these are correct? "Y" for yes, "N" for no: ')
        valid = input()
    loginData.writelines(['DO NOT DELETE THIS FILE, unless your username or password have changed','\n',username,'\n',password])
    
    loginData.close()
    loginData = open(Path.home()/'loginData.txt','r')
    
# Get username and password from file
loginStrings = loginData.readlines()
username = loginStrings[1]
username = username[:-1]
password = loginStrings[2]

# Set options for edge tab
options = webdriver.EdgeOptions()
options.add_experimental_option('detach',True)
options.add_experimental_option('useAutomationExtension',False)
options.add_experimental_option("excludeSwitches",["enable-automation"])
options.add_argument('--start-maximized')

# Open edge and sign into blackboard
driverPath = Service('msedgedriver.exe')
driver = webdriver.Edge(service=driverPath,options=options)
driver.get('https://blackboardlearn.utep.edu/')

driver.find_element('id', 'agree_button').click()
driver.find_element('id', 'redirectProvidersDropdownButton').click()
driver.find_element('class name','defaultProviderIcon').click()
driver.find_element('id','usernameUserInput').send_keys(username)
driver.find_element('id','password').send_keys(password)
driver.find_element('xpath','//*[@id="loginForm"]/div[4]/div/button').submit()