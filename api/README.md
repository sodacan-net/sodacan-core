# SodaCan API
The SodaCan API provides programmatic access to most of the capabilities of SodaCan. This API is used by the REST API and by the command line tool. The SodaCan agent also uses the API to access SodaCan functions.

The backside of the SodaCan API is a connection to and from the SodaCan message bus. Since the message passing architecture is asynchronous, many of the APIs provide a "future" to the caller to wait for a response.

