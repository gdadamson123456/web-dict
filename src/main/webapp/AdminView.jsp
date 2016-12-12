<html>
<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/pure/0.6.0/pure-min.css">
    <link href="../css/main.css" rel="stylesheet" type="text/css">
    <title>Admin</title>
</head>
<body>
<jsp:include page="/Header.jsp"/>
<div id="container">
    <div id="containerMain"></div>
    <div id="containerEdit"></div>
</div>
<script type="text/template" id="viewUsers">
    <h2 align='center'><a href="#add" id="addNewUser">Add new user</a></h2>
    <table id="usersTable" class="table pure-table pure-table-bordered shadow op" align="center" border="2">
        <thead>
        <th>Login</th>
        <th>Role</th>
        <th>Actions</th>
        </thead>
        <tbody id="tableBody" class="UsersList"></tbody>
    </table>
</script>
<script type="text/template" id="viewUser">
    <td> <\%=login%></td>
    <td> <\%=role.name%></td>
    <td>
        <a href="#edit/<\%= id %>">Edit</a> <a href="#delete/<\%= id %>" id="delete">Delete</a>
    </td>
</script>
<script type="text/template" id="editView">
    <div align="center">
        <h1 id="header">
            Edit
        </h1>
        <form method='post' class="pure-form shadow op block">
            <table id="editTable">
                <tr><p id="loginOrEmail"></p></tr>
                <tr>
                    <td>Login</td>
                    <td><input type="text" value="<\%= login %>" id="login"/></td>
                    <td><p id="loginError"></p></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password" value="<\%= password %>" id="password"/></td>
                    <td><p id="passwordError"></p></td>
                </tr>

                <tr>
                    <td>Password confirm</td>
                    <td><input type="password" value="<\%= password %>" id="passwordAgain"/></td>
                    <td><p id="passwordRepeatError"></p></td>
                </tr>

                <tr>
                    <td>Email</td>
                    <td><input type="email" value="<\%= email %>" id="email"/></td>
                    <td><p id="emailError"></p></td>
                </tr>

                <tr>
                    <td>Role</td>
                    <td><select name="role" id="roleSelect">
                        <option value="ROLE_admin"
                        <\%= (role.name === "ROLE_admin") ? 'selected' : '' %> >Admin </option>
                        <option value="ROLE_user"
                        <\%= (role.name === "ROLE_user") ? 'selected' : '' %> >User </option>
                    </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="3" align="left">
                        <div>
                            <input type="button" value="Ok" id="submit" class="pure-button pure-button-primary"/>
                            <input type="button" value="Cancel" class="pure-button" id="cancel"/>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</script>
<script src="js/jquery-2.1.4.js"></script>
<script src="js/underscore.js"></script>
<script src="js/backbone.js"></script>
<script src="js/main.js"></script>
</body>
</html>