<div align="center">
  <h1>CozyPetsHotel</h1>
  <img src="https://github.com/MileneAngelova/CozyPetsHotel/blob/7388fdb828e311c2cfc85f72fcb74409215f084c/src/main/resources/static/images/logo.png" alt="PetsHotel's logo" width="40%" style="background-color: darkgoldenrod">
  <p>Welcome to Cozy Pets Hotel!</p>
</div>


Cozy Pets Hotel is a web application, designed to facilitate people with a travelling journey
to provide the highest level of care for their pets, during their absence.

This project utilizes a microservices architecture with REST APIs for communication between services.</p>
## Project Overview

The project consists of two main components:

1. **CozyPetsHotel (Gradle Project)**: The core backend of the platform.
2. **CozyPetsBookings (Gradle Project)**: Manages the bookings.

## Technologies Used

### Back-end:
- **Spring Boot**
- **Spring Data JPA**: For data persistence and repository management
- **Spring Security**: Manages user authentication and authorization.
- **MySQL**: Database to store user data, bookings, and other information.
- **ModelMapper**: For object mapping between DTOs and entities.
- **REST API**: Utilizes RESTful services for communication between the core application and the booking management.

### Front-end:
- **JavaScript**
- **HTML/CSS**
- **Template Engine/Framework**: Uses Thymeleaf for server-side rendering and Bootstrap for styling and responsive design.

## Features

- **User registration and authentication**: Create an account and log in securely.
- **Make bookings**: Choosing dates and entering the correct pet information.
- **Delete bookings**: Cancel the reservation if necessary.
- **Profile Management**: Users and administrators can edit their profiles.
- 
### Database

- **Database**: MySQL.
- **Spring Data JPA**

### Security

- **Spring Security**
- **Roles**: Differentiates between user and administrator roles.

### Validation and Error Handling

- **Validation**: Both client-side and server-side validation mechanisms.
- **Error Messages**: Informative validation messages for users.

## Screenshots

Here are some screenshots of the Dam Platform in action:

- **Home Page**
  ![Home Page](/src/main/resources/static/images/screenshots/Index.png)

- **Register Page**
  ![Register Page](/src/main/resources/static/images/screenshots/Register.png)

- **Services Page**
  ![Services Page](/src/main/resources/static/images/screenshots/Services.png)

- **Gallery Page**
  ![Gallery Page](/src/main/resources/static/images/screenshots/Galellry.png)

- **User Page**
  ![User Page - Settings](/src/main/resources/static/images/screenshots/User-Settings.png)

- **Admin Page**
  ![Admin Page - All Bookings](./src/main/resources/static/images/screenshots/Admin-Users.png)

