# OkHttp3Extended

This is the extended version of OkHttp3 Library. Which makes networking in Android easier than ever. This library is very easy to use. This library is used to get only Json Response using get and post method only it does not support other type of requests as of now.

# How to use

1. Add CreateConnection.jar file as a dependency in your Android Project                              
                                **OR**                      
   Place CreateConnection.jar file in app/libs folder and then add 
   
    ```
    implementation files('libs/CreateConnection.jar')
    ```
    
   in your project level build.gradle.
   
2. Add Internet Permissions in your manifest file.

   ```<uses-permission android:name="android.permission.INTERNET"/>```
   
   and 
   ```implementation 'com.squareup.okhttp3:okhttp:3.12.1'```
   in your module level build.gradle
   
   For Android 8 OREO or above you also need to create a network security configuration file where you need to define where    do you want to send request from your application. To do this create file res/xml/network_security_config.xml
   
   ```
   <?xml version="1.0" encoding="utf-8"?>
   <network-security-config>
      <domain-config cleartextTrafficPermitted="true">
        <domain includeSubdomains="true">Your URL(ex: 127.0.0.1)</domain>
       </domain-config>
   </network-security-config>
   ```
   
   and then include this file in your AndroidManifest.xml file
   
   ```
   <?xml version="1.0" encoding="utf-8"?>
   <manifest ...>
       <uses-permission android:name="android.permission.INTERNET" />
       <application
           android:networkSecurityConfig="@xml/network_security_config"
       ...>
       </application>
   </manifest>
    ```
    
    **OR**
    
    If you want to allow your app to send request to all domain/ip use this directly in your AndroidManifest.xml file
    ```
    <?xml version="1.0" encoding="utf-8"?>
    <manifest ...>
       <uses-permission android:name="android.permission.INTERNET" />
       <application
           android:usesCleartextTraffic="true"
        ...>
       </application>
     </manifest>
     ```
    
3. Create a CreateConnection Object when you want to make a network request

   ```CreateConnection createconnection = new CreateConnection(baseUrl);```
   
   base Url should not include file name where you want to send request
   
   **For  Example**
   
   http://domain.com is correct where as http://domain.com/signin.php is wrong
   
   **You Don't need to call CreateConnection object in AsynTask or on background thread. The Library handles it itself.**

4. Once you are done with the above step you are good to go now. To create a new request to server you have to create a RequestBody. Request body contains the parameters that you want to send with request to server.

If you want to request using **GET** Method please use null in place of RequestBody object at the time of function call and provide parameteres in url only.

   **Creating Request Body**
   
    ```
    RequestBody requestBody = new FormBody.Builder()
                .add("name", value)
                .add("name", value).build();
    ```
               
5. Now, To create a actual connection with server you need to call createRequest function.

   **If You want that the library itself shows progress dialog while connecting with server use this method**
     
     ```
     createConnection.createRequest(String url, RequestBody requestBody, Context context)
     ```

   **If you manually want to show progress dialog use this method**'
     
     ```
     createConnection.createRequest(String url, RequestBody requestBody)
     ```
     
# Getting Response From Server

To get JSON response from server you need to add response listener on your CreateConnection object
  
   ```
   createConnection.setResponseListener(new ResponseListener() {     
            @Override
            public void onResponseReceived(String s) {
               //Response Successfully received
            }
            @Override
            public void onResponseFailed() {
                //Response Failed
            }
        });
   ```
# Changing the URL after Object Creation
 
To change BaseUrl you can call changeBaseUrl() method

```createConnection.changeBaseUrl(URL)```
