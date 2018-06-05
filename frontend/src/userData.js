import $ from "jquery";

let instance;

class UserData {
    constructor() {
        if (instance) {
            return instance;
        }

        this.user = this.getUser();
        this.apiKey = this.user["apiKey"];
        this.userID = this.user["userID"];

        instance = this;
    }

    getUser() {
        let user;
        $.ajax({
            url: "/login/user",
            type: "GET",
            dataType: "json",
            cache: false,
            async: false,
            success: function (data) {
                user = data;
            },
            error: function (xhr, status, err) {
                console.error(status, err.toString());
            }
        });
        return user;
    }
}

export default UserData;