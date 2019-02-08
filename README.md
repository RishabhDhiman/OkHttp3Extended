# OkHttp3Extended

This is the extended version of OkHttp3 Library. Which makes networking in java easier than ever. This library is very easy to use. This library is used to get only Json Response using post method only it does not support other type of response as of now.

# How to use
1. Add this jar file as a dependency in your Java Project
2. Create a CreateConnection Object where you want to make a network request

   **CreateConnection createconnection = new CreateConnection(baseUrl);**
   
   base Url should not include file name where we want to send request
   
   http://domain.com is correct where as http://domain.com/signin.php is wrong
   
3. Once you are done with the above step you are good to go now. To create a new request to server you have to create a -RequestBody. Request body contains the parameters that you want to send to server.

   **Creating Request Body**
   
   RequestBody requestBody = new FormBody.Builder()
                .add("name", value)
                .add("name", value).build();
               
4. Now, To create a actual connection with server you need to call createConnection function.

   **If You want that the library itself shows progress dialog while connecting with server use this method**
     
     createRequest(String url, RequestBody requestBody, Context context)

   **If you manually want to show progress dialog use this method**'
     
     createRequest(String url, RequestBody requestBody)
