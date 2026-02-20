# android_template

## 📱 Android MVVM Base Project

This project is built using MVVM architecture with clean structure and proper API handling.

The goal is to keep the code scalable, maintainable, and easy to extend.

### 🚀 What Has Been Implemented

**🔹 API Setup**

   - Retrofit API client setup
   - Base URL configuration
   - API Service interface created
   - Centralized API calling
   

**🔹 Common API Response Model**

   - Created BaseResponse class
   - Handles:
		- statusCode
		- statusMessage
		- data
   - Added isSuccess() method (statusCode == 200)
   
**🔹 Repository Layer**

   - Created BaseRepository
   - All API calls pass through it
   - Handles:
	   - Loading state
	   - HTTP errors (401, 500 etc.)
	   - Parsing errorBody()
	   - Business errors (statusCode != 200)
	   - Empty data handling
   - Feature repository:
       - AuthRepository
	   
**🔹 ViewModel Layer**

   - Created AuthViewModel
   - ViewModel calls repository
   - UI observes LiveData
   - No API logic inside Activity

**🔹 ApiResult Wrapper**

   - Created a common result class to manage:
		- LOADING
		- SUCCESS
		- ERROR
   - This helps UI react properly based on API state.

**🔹 Generic ViewModel Factory**

   - Single AppViewModelFactory
   - No multiple factory classes
   - Scalable for unlimited ViewModels
   - Uses Map + Supplier approach

**🔹 ViewModel Initialization from Application Class**

   - ViewModels registered in MyApplication
   - Factory created once
   - Activities get ViewModel using:
    
	new ViewModelProvider(this, factory).get(AuthViewModel.class);

 This avoids creating separate factories.

### 📱 Screens Implemented

   - ✅ Splash Activity
   - ✅ Login Activity
   - ✅ Signup Activity
   - ✅ Main Screen (currently blank)

### 🛠 Utility Classes

   - ✅ NetworkUtil → Check internet connection
   - ✅ SharedPref Manager → Store token & user data
   - ✅ AppToast → Reusable toast class
   - ✅ Validation → Input validation methods

### 🆕 Improvements Done Recently

   - Improved BaseRepository
   - Added proper HTTP 401 handling
   - Parsed response.errorBody() correctly
   - Fixed login error not showing in UI
   - Cleaned ViewModel logic
   - Removed unnecessary null checks from UI
   - Moved API validation responsibility to Repository
   - Improved overall scalability

## 🏗 Current Flow

	Activity
		↓
	ViewModel
		↓
	Repository
		↓
	BaseRepository
		↓
	Retrofit API


## 🎯 Current Project Status

   - Clean MVVM structure
   - Centralized API handling
   - Proper error management
   - Scalable ViewModel creation
   - Internet checking implemented
   - Token storage implemented
   - Login flow working
   - Invalid login error handled correctly
   
   
### 👨‍💻 Project Type

	Android App (Java) – MVVM + Repository Pattern   