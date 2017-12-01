# java_multithreaded_client_server
D.I.T. assignment DEC 2017

### TASK 
Java program based on the following requirements:

Create a multithreaded socket server program to allow a client to request html markup for a given url. The server will cache all of the pages that have been  previously requested. The client will send two data variables to the server:
* Page URL variable eg. (http://www.dit.ie)
* No_Cache variable eg. (true or false)

If no_cache is true, the server will request the markup of the page using the URL class. If no_cache is false, the server will first check the cache to see if the page was requested previously. If it was not requested before, request the markup of 
the page. If the page was requested previously return the markup stored in the cache.

Provide programmatic control to ensure the threaded program can synchronize access to the cache.

Hints: An ArrayList could be used to offer the cache functionality. (ie. An ArrayList that stores an object [url and markup])

