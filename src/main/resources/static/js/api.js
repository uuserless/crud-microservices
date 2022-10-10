const userFetch = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json; charset=UTF-8',
        'Referer': null
    },

    getCurrentUser: async () => await fetch('http://localhost:8080/api/currentUser').then(res => res.json()),
    getAllUsers: async () => await fetch('http://localhost:8080/api/allUsers').then(res => res.json()),
    getUserById: async (id) => await fetch('http://localhost:8080/api/show/' + id).then(res => res.json()),
    getAllRoles: async () => await fetch('http://localhost:8080/api/allRoles').then(res => res.json()),
    addUser: async (user) => await fetch('http://localhost:8080/api/new', {
        method: "POST",
        headers: userFetch.head,
        body: JSON.stringify(user)
    }),
    updateUser: async (id, user) => await fetch(`http://localhost:8080/api/edit/` + id, {
        method: 'PUT',
        headers: userFetch.head,
        body: JSON.stringify(user)
    }),
    deleteUserByID: async (id) => await fetch(`http://localhost:8080/api/delete/` + id, {
        method: 'DELETE',
        headers: userFetch.head
    })

}


$(async function() {
    await userHeader()
});

function userHeader() {
    userFetch.getCurrentUser()
        .then(data => {
            $('#header-username').append(data.username)
            $('#header-roles').append(data.roles)
        })
}

$(async function() {
    await getAdminPanel()
});

async function getAdminPanel() {
    $('#allUsers-table').empty()
    userFetch.getAllUsers()
        .then(data => {
            data.map(user => {
                let newCol = document.createElement('tr');
                newCol.innerHTML = `
                    <td>${user.id}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${user.roles}</td>
                    <td><button type="button" class="btn btn-info text-light" data-bs-toggle="modal" id="buttonEdit"
                                data-action="edit-modal" data-bs-target="#edit-modal" onclick="showEditModal(${user.id})">Edit</button></td>
                    <td><button type="button" class="btn btn-danger" data-bs-toggle="modal" id="buttonDelete"
                                data-action="delete-modal" data-bs-target="#delete-modal" onclick="showDeleteModal(${user.id})">Delete</button></td>
                `;
                $('#allUsers-table').append(newCol);
            })
        })
}

async function showEditModal(id) {
    $('#editRoles').empty()
    let user = await userFetch.getUserById(id)
    let form = document.forms['editModalForm'];
    form.editId.value = user.id;
    form.editFirstName.value = user.firstName;
    form.editLastName.value = user.lastName;
    form.editAge.value = user.age;
    form.editEmail.value = user.email;
    form.editPassword.value = user.password;

    await userFetch.getAllRoles()
        .then(roles => {
            roles.map(role => {
                let selectedRole = false;
                console.log(role)
                for (let i = 0; i < user.roles.length; i++) {
                    if (user.roles.role === role.role) {
                        selectedRole = true;
                        break;
                    }
                }
                let el = document.createElement('option');
                el.text = role.role.substring(5);
                el.value = role.id;
                if (selectedRole) el.selected = true;
                $('#editRoles').append(el);
            })
        })
}

async function showDeleteModal(id) {
    $('#deleteRoles').empty()
    let user = await userFetch.getUserById(id)
    let form = document.forms['deleteModalForm'];
    form.id.value = user.id;
    form.firstName.value = user.firstName;
    form.lastName.value = user.lastName;
    form.age.value = user.age;
    form.email.value = user.email;

    await userFetch.getAllRoles()
        .then(roles => {
            roles.map(role => {
                let el = document.createElement('option');
                el.text = role.role.substring(5);
                el.value = role.id;
                $('#deleteRoles').append(el)
            })
        })
}


$(async function() {
    await editUser()
})

function editUser() {
    const form = document.forms['editModalForm'];
    form.addEventListener('submit', ev => {
        ev.preventDefault();
        let editUserRoles = [];
        for (let i = 0; i < form.roles.options.length; i++) {
            if (form.roles.options[i].selected) editUserRoles.push({
                id: form.roles.options[i].value,
                role: "ROLE_" + form.roles.options[i].text
            })
        }

        const user = {
            id: form.editId.value,
            firstName: form.editFirstName.value,
            lastName: form.editLastName.value,
            age: form.editAge.value,
            email: form.editEmail.value,
            password: form.editPassword.value,
            roles: editUserRoles
        }

        userFetch.updateUser(form.editId.value, user)
            .then(async () => {
                $('#edit-close-button').click();
                await getAdminPanel();
            })
    })
}

$(async function() {
    deleteUser();
});

function deleteUser() {

    const form = document.forms['deleteModalForm'];
    form.addEventListener('submit', ev => {
        ev.preventDefault();
        userFetch.deleteUserByID(form.id.value)
            .then(async () => {
                $('#delete-close-button').click();
                await getAdminPanel();
            })
    })
}

$(async function() {
    await newUser();
});

async function newUser() {

    await userFetch.getAllRoles()
        .then(roles => {
            roles.map(role => {
                let el = document.createElement('option');
                el.text = role.role.substring(5);
                el.value = role.id;
                $('#new-user-option').append(el)
            })
        })

    const form = document.forms['new-user-form'];
    form.addEventListener('submit', ev => {
        ev.preventDefault();
        let newUserRoles = [];
        for (let i = 0; i < form.roles.options.length; i++) {
            if (form.roles.options[i].selected) newUserRoles.push({
                id: form.roles.options[i].value,
                role: "ROLE_" + form.roles.options[i].text
            })
        }

        const user = {
            firstName: form.firstName.value,
            lastName: form.lastName.value,
            age: form.age.value,
            email: form.email.value,
            password: form.password.value,
            roles: newUserRoles
        }

        userFetch.addUser(user).then(() => {
            form.reset();
            getAdminPanel();
            $(document).ready(function() {
                $('#new-user-nav').removeClass('active')
                    .removeAttr('aria-selected')
                    .attr('tabindex', -1);
                $('a[href="#all-users-panel"]').addClass('active')
                    .attr('aria-selected', 'true')
                    .removeAttr('tabindex');
                $('#all-users-panel').addClass('active')
                    .addClass('show');
                $('#new-user-panel').removeClass('active')
                    .removeClass('show');
            });
        })
    })
}

$(async function() {
    await getUserPanel()
})

async function getUserPanel() {

    userFetch.getCurrentUser()
        .then(user => {
            let tableWithUser = $(`<tr>
                    <td>${user.id}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${user.roles}</td>
                </tr>`);
            $('#currentUser-table').empty().append(tableWithUser)
        })
}