# SodaCan Web Server
This Web Server hosts static web pages and the RESTful API (which in turn used the SodaCan API). The static pages provide the Soda Can control panel, dashboard, and administrative capabilities in a graphical format with similar capabilities to the Command Line Interface (CLI).

The RESTful API is used by the static pages and can also be used instead of the SodaCan API for message input and output to programs that might otherwise not be able to communicate over ports other than HTTP and HTTPS.

### Web Page Layout
The primary User Interface is broken down into these sections:

#### User Account
This section covers account login and logout and the ability for a user to edit their profile.

#### Administration
Maintain topics, modes, clocks, modules, and messages

#### Operations
This page allows start and stop server(s) and monitor their operation.
Monitor message bus storage and message traffic.
Monitor actual message traffic.
Publish messages
#### Application Console
There are two major categories of pages in this section: Generic and Custom.
Generic pages require no configuration. Modules and their state are displayed in a general layout providing a full picture of the system. Each module is represented by a a generic widget. Generic pages are organized by mode and domain. All modules in each group are displayed alphabetically. 
If a module has instances, clicking on the top-level module drills down to its instances.

Custom pages provide access to selected subsets of widgets. Pages can be organized as needed by the user and modules within a page can be either the top-level module or specific instances or both. Color and other attributes can be changed.

In either type of page, the widgets allow the user to create messages.

> The generic pages provide a dynamic view of *all* modules. It also acts as a palette when setting up custom pages.

### Authentication
A user must have an account in order to log in. SodaCan uses a browser cookie to remember a user's session.
In SodaCan, sessions usually don't time out (log out automatically). 


### Authorization
The system administrator can assign specific permissions to individual users.

### Implementation
HTML is served statically. The pages access the SodaCan RESTful API. Many aspects of the user interface are updated dynamically using Server Sent Events (SSE). No polling needed. This provides a relatively low bandwidth means of displaying the current state of modules. A page opens a single "subscription" channel to the server which in turn sends module definitions (JSON SodaCan module AST format) and variable updates. 

Bootstrap and JQuery are used extensively in the HTML pages.

