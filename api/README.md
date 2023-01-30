# Sodacan API
The Sodacan API provides programmatic access to most of the capabilities of Sodacan. This API is used by the REST API and by the command line tool. The Sodacan agent also uses the API to access Sodacan functions.

The backside of the Sodacan API is a connection to and from the Sodacan message bus. Since the message passing architecture is asynchronous, many of the APIs provide a "future" to the caller to wait for a response.

