# Customer Service Messaging App

A simple messaging app for customer service agents to log in, view customer message threads, and respond.

## Tech Stack

- **Language**:  Kotlin
- **UI**: XML
- **Architecture**: MVVM (Model-View-ViewModel)
- **Network**: Retrofit with Interceptor for Auth Token
- **Dependency Injection**: Dagger
- **Data Storage**: SharedPreferences (for saving auth token and user data)

## Features

- **Login Screen**
  - Agents log in using their email address.
  - Password is the reverse of the email address.
  - Displays an error for invalid credentials.
  - On successful login, stores the authentication token for future requests.

- **Threads List**
  - Displays a list of message threads with the latest message.
  - Shows message body, timestamp, and sender (agent or user ID).
  - Tapping a thread navigates to the conversation screen.

- **Conversation Screen**
  - Shows a sorted list of all messages in a thread.
  - Displays message body, timestamp, and sender.
  - Allows agents to send replies using an input field.

