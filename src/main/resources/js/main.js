document.app = document.app || {};

document.app.UserModel = Backbone.Model.extend({
    urlRoot: 'api/rest/users',
    idAttribute: 'id',
    defaults: {
        id: '',
        login: '',
        password: '',
        email: '',
        firstName: '',
        lastName: '',
        birthday: '',
        role: {
            id: 2,
            name: 'user'
        }
    }
});
document.app.UserCollection = Backbone.Collection.extend({
    model: document.app.UserModel,
    url: 'api/rest/users'
});
document.app.UserView = Backbone.View.extend({
    tagName: 'tr',
    initialize: function () {
        this.template = _.template($('#viewUser').html());
        this.model.attributes.age = this.getAge(this.model.get('birthday'));
    },
    render: function () {
        var view = this.template(this.model.toJSON());
        this.$el.html(view);
        return this.$el;
    },
    getAge: function (date) {
        var today = new Date();
        var birthDate = new Date(date);
        var age = today.getFullYear() - birthDate.getFullYear();
        var m = today.getMonth() - birthDate.getMonth();
        if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
            age--;
        }
        return age;
    }
});
document.app.UsersView = Backbone.View.extend({
    defaults: {
        users: document.app.usersList
    },
    initialize: function () {
        this.template = _.template($('#viewUsers').html());
        this.$el.html(this.template());
        this.users = document.app.usersList;
        this.listenTo(this.users, "change", this.render);
        this.listenTo(this.users, "remove", this.render);
    },
    render: function () {
        this.$('#tableBody').empty();
        this.users.forEach(this.renderUser, this);
    },
    renderUser: function (model) {
        var view = new document.app.UserView({
            model: model
        });
        this.$('#tableBody').append(view.render());
    }
});
document.app.EditView = Backbone.View
    .extend({
        initialize: function () {
            this.template = _.template($('#editView').html());
            this.render();
        },
        render: function () {
            var view = this.template(this.model.toJSON());
            this.$el.html(view);
            this.isEdit = this.model.attributes.id;
            if (this.isEdit) {
                this.$el.find('#header').html('Edit user');
                this.$el.find('#login').prop('readonly', true);
            } else {
                this.$el.find('#header').html('Add user');
                this.$el.find('#login').prop('readonly', false);
            }
            this.$el.find("p").addClass("errors");
            return this.$el;
        },
        events: {
            'click #submit': 'submit',
            'click #cancel': 'cancel'
        },
        submit: function () {
            this.setDataToModel();
            var modelAttributes = this.model.attributes;
            var errors = this.validate(modelAttributes) || [];
            this.$el.find("p").text('');
            if (errors.length > 0) {
                this.showErrors(errors);
            } else {
                var that = this;
                this.saveModel(function (hasErrors) {
                    if (!hasErrors) {
                        that.goToMainPage();
                    }
                });
            }
        },
        cancel: function () {
            this.goToMainPage();
        },
        setDataToModel: function () {
            var that = this;
            this.$el.find('input[id]').each(function () {
                var attrName = this.id;
                var attrValue = this.value;
                that.model.set(attrName, attrValue);
            });
            var roleName = this.$el.find("#roleSelect option:selected")
                .text().trim();
            this.model.set("role", roleName === "Admin" ? {
                id: 1,
                name: 'admin'
            } : {
                id: 2,
                name: 'user'
            });
            if (!this.isEdit) {
                this.model.attributes.id = null;
            }
        },
        validate: function (attrs) {
            var errors = [];
            if (attrs.login.length < 2) {
                console.log("minimum login size is 2 symbols");
                errors.push({
                    name: 'loginError',
                    message: "minimum login size is 2 symbols"
                });
            }
            var nameRegExp = /^([a-zA-Z]+)$/;
            if (!nameRegExp.test(attrs.login)) {
                console.log("Login must consist only symbols");
                errors.push({
                    name: 'loginError',
                    message: "Login must consist only symbols"
                });
            }
            if (attrs.password.length < 4) {
                console.log("Min password size = 4");
                errors.push({
                    name: 'passwordError',
                    message: "Min password size = 4"
                });
            }
            var passRegExp = /^([a-zA-Z])*([0-9])*$/;
            if (!passRegExp.test(attrs.password)) {
                console.log("password must consist symbols and digits");
                errors.push({
                    name: 'passwordError',
                    message: "password must consist symbols and digits"
                });
            }
            if (attrs.email.length < 3) {
                console.log("minimum email size is 3 symbols");
                errors.push({
                    name: 'emailError',
                    message: "minimum email size is 3 symbols"
                });
            }
            var emailRegExp = /^(\w+.?)+@(\w+.?)+$/;
            if (!emailRegExp.test(attrs.email)) {
                console
                    .log("Email must be like this pattern [someAddress]@[someDomain]");
                errors
                    .push({
                        name: 'emailError',
                        message: "Email must be like this pattern [someAddress]@[someDomain]"
                    });
            }
            if (attrs.firstName.length < 2) {
                console.log("minimum firstName size is 2 symbols");
                errors.push({
                    name: 'firstNameError',
                    message: "minimum firstName size is 2 symbols"
                });
            }
            if (!nameRegExp.test(attrs.firstName)) {
                console.log("firstName must consist only symbols");
                errors.push({
                    name: 'firstNameError',
                    message: "firstName must consist only symbols"
                });
            }
            if (attrs.lastName.length < 2) {
                console.log("minimum lastName size is 2 symbols");
                errors.push({
                    name: 'lastNameError',
                    message: "minimum lastName size is 2 symbols"
                });
            }
            if (!nameRegExp.test(attrs.lastName)) {
                console.log("lastName must consist only symbols");
                errors.push({
                    name: 'lastNameError',
                    message: "lastName must consist only symbols"
                });
            }
            var dateRegExp = /^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
            if (!dateRegExp.test(attrs.birthday)) {
                console
                    .log("Incorrect date format. Please, check format for next pattern yyyy-mm-dd");
                errors
                    .push({
                        name: 'birthdayError',
                        message: "Incorrect date format. Please, check format for next pattern yyyy-mm-dd"
                    });
            }
            if (attrs.password !== this.$el.find('#passwordAgain').val()) {
                console.log("passwords isn't same");
                errors.push({
                    name: 'passwordRepeatError',
                    message: "passwords isn't same"
                });
            }
            if (errors.length > 0) {
                return errors;
            }
        },
        showErrors: function (errors) {
            _.each(errors, function (error) {
                var errorField = $('#' + error.name, this.$el).empty();
                errorField.text(error.message);
            }, this);
        },
        saveModel: function (hasErrorsCallback) {
            if (!this.isEdit) {
                this.createUser(hasErrorsCallback);
            } else {
                this.updateUser(hasErrorsCallback);
            }
        },
        createUser: function (hasErrorsCallback) {
            var that = this;
            this.model.save({}, {
                success: function () {
                    document.app.usersList.add(that.model);
                    hasErrorsCallback(false);
                },
                error: function (model) {
                    that.setErrorsFromServer(model);
                    hasErrorsCallback(true);
                }
            });
        },
        updateUser: function (hasErrorsCallback) {
            var that = this;
            this.model.save(null, {
                success: function () {
                    hasErrorsCallback(false);
                },
                error: function (model) {
                    that.setErrorsFromServer(model);
                    hasErrorsCallback(true);
                }
            });
        },
        goToMainPage: function () {
            window.location = "#";
        },
        setErrorsFromServer: function (errorModel) {
            var errors = [];
            if (errorModel != null && (!errorModel.login)
                || (!errorModel.email)) {
                errors.push({
                    name: 'loginOrEmail',
                    message: "Login or Email exists"
                });
            }
            this.showErrors(errors);
        }
    });
var hide = function (id, callback) {
    $(id).hide('fast', callback);
};
var show = function (id, callback) {
    $(id).show('fast', callback);
};
var showEdit = function (id) {
    hide("#containerMain", function () {
        var user = id ? document.app.usersList.get(id)
            : new document.app.UserModel();
        if (document.app.editView) {
            document.app.editView.model = user;
            document.app.editView.render();
        } else {
            document.app.editView = new document.app.EditView({
                el: '#containerEdit',
                model: user
            });
        }
        show('#containerEdit');
    });
};
var showMain = function () {
    hide("#containerEdit", function () {
        if (!document.app.userView) {
            document.app.userView = new document.app.UsersView({
                el: '#containerMain'
            });
        }
        document.app.userView.render();
        show('#containerMain');
    });
};
var removeUser = function (id) {
    var result = confirm("Are you sure?");
    if (result) {
        var current = new document.app.UserModel({
            id: id
        });
        document.app.usersList.remove(current);
        current.destroy({
            success: function (model, response, options) {
                window.location = "#";
            }
        });
    }
};
document.app.appRouter = Backbone.Router.extend({
    routes: {
        "delete/:id": 'remove',
        "edit/:id": 'edit',
        "add": 'add',
        "#": 'index',
        "": 'index'
    },
    remove: function (id) {
        removeUser(id);
    },
    edit: function (id) {
        showEdit(id);
    },
    add: function () {
        showEdit();
    },
    index: function () {
        document.app.usersList.fetch({
            success: function (collection, response, options) {
                showMain();
            }
        });
    },
    initialize: function () {
        document.app.usersList = new document.app.UserCollection();
        window.location = "#";
    }
});
$(function () {
    document.app.router = new document.app.appRouter();
    Backbone.history.start();
});