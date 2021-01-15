# Posture App Project

##### Last updated: 15/01/2021

This application was developed by Oliver Barbaresi, Alice Duhem, Callan Egan and Nikita Narayanan as a final project for the Programming 3 course in the departement of Bioengineering at Imperial College London.

The project is "A posture monitoring system for prevention of ulcer formation in chair-bound individuals". The main aim was to create an intuitive software that nurses will use to know when they need to reposition a patient.
The web application was deployed in Heroku and accessible at the following address: http://postureapp.herokuapp.com/login

As the user interface and user experience is a core requirement of the software, Vaadin was used. Vaadin is an open source framework which enables to create web applications using java. The project is organised by:

    Backend: All the classes and Database tables which defines the components visible in the UI and the logic 
    * Entities:
        Defines class and their methods
    * Repositories:
        Database, sql command queries from the database
    * Service:
        Communicates with the repository to add and get information from the database

     Security: Defines the login security settings and who can access the application

     Views: User interfaces visible in the application. It calls the services to display information
 
**Flow of the application**

1. Login using your login and password  

2. If login successful, the main view, ward view is displayed. In this view:
      * Add a patient with name, bed number, bradenscore:
        - The patient is added to the database
        - The patient is added to a bed box
        - The posture status bar and the time in position changes according to the pressure data coming from the bed
          - This pressure is compared to the previous one to see if the patient reposition or if he is in a abnormal posture
          - Currently, the process is accelerated by 60 times
        - Each time data comes in and is unchanged from the previous one, the time in position increase 
      * Add a patient with ID and bed number:
        - The patient ID is queried in the database and his information are added to the bed box in the view
      * Remove a patient with Bed number:
        - The patient is removed from the bed in the UI and the bed is set as empty
      * Reset the patient posture with Bed number:
        - When a nurse repositions the patient and resets the bed posture, the time in position resets to 0 and the status bar resets
        
The following views are not linked yet to the ward view. For further development, nurses/wards login in will access the value of their ward. The hospital admin view will be accessed by the software administrator and the nurse assignement by the ward or hospital administrator in a specific hospital
        
3. The Hospital Admin View
      * Add a hospital in the database with name and postcode
      * Add a ward in the database using the hospital postcode and the ward name 
        - The hospital needs to exist in the database to add the ward
        
4. The Nurse assignment View
      * Add a nurse to the database with a name, email and ward name (which would correspond to the hospital, not yet implemented). The ward must already exist in the database
      * Reassign the nurse to another ward with an email and ward name
      
