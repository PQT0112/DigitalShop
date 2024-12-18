<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Edit Profile</title>

    </head>

    <body>
        <h1>Edit Profile</h1>

        <form:form modelAttribute="user" action="/client/profile/update" method="post" enctype="multipart/form-data">
            <!-- Full Name -->
            <div class="form-group">
                <label for="fullName">Full Name</label>
                <form:input path="fullName" id="fullName" class="form-control" />
            </div>

            <!-- Address -->
            <div class="form-group">
                <label for="address">Address</label>
                <form:input path="address" id="address" class="form-control" />
            </div>

            <!-- Phone -->
            <div class="form-group">
                <label for="phone">Phone</label>
                <form:input path="phone" id="phone" class="form-control" />
            </div>

            <!-- Email -->
            <div class="form-group">
                <label for="email">Email</label>
                <form:input path="email" id="email" class="form-control" readonly="true" />
            </div>



            <!-- Submit Button -->
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Update Profile</button>
            </div>
        </form:form>

    </body>

    </html>