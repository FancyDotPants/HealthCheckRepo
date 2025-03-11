## Health Check Program with Java
- Description: This is a simple command line tool program which checks if the user if they are okay or sick in every 2 hours. if the user doesn't respond in the next one minute the program will access the contacts and send an emergency SMS to a given number.


# Other Instructions 

1. Permissions Required:
 - Allow Terminal/Java access to send messages in:
 - System Preferences → Security & Privacy → Privacy → Automation
 - Enable "osascript" and Messages for your terminal emulator

2. Features:
 - Runs every 2 hours
 - Gives 3 minutes to respond
 - Validates responses ("im okay" or "im sick")
 - Sends emergency SMS via Messages app if:
    - No response
    - Invalid response
    - Any error occurs

3. To Use:
  ``
   javac HealthCheck.java
   java HealthCheck
  ``
4. Limitations/Customization:
 - Change PHONE_NUMBER constant for different emergency number
 - Modify SMS content in emergencyProcedure
 - Adjust time intervals in scheduleAtFixedRate
 - To use iMessage instead of SMS, change service name to "iMessage"

Dependencies:
- macOS with Messages app configured
- AppleScript permissions enabled
- Java 8 or newer

This implementation uses macOS's built-in Messages application for emergency notifications, ensuring better reliability than third-party services. The program will run continuously until manually terminated (Ctrl+C).
