<template>
    <UpdateProfissionalAdministrador :user="user" :resetname="userResetName" @update="onSubmit"/>
</template>

<script>
import UpdateProfissionalAdministrador from "../../../components/UpdateProfissionalAdministrador";
import administradoresViewPostPut from "../../../middleware/administradoresViewPostPut";

export default {
    components: {UpdateProfissionalAdministrador},
    middleware: administradoresViewPostPut,
    data() {
        return {
            currentpassword: null,
            newpassword: null,
            user: null,
            userResetName: ""
        }
    },
    methods:{
        onSubmit(data) {
            this.user.currentpassword= data.currentpassword
            this.user.newpassword = data.newpassword

            this.$axios.$put("/api/administradores/" + this.$route.params.email, this.user).then(response => {
                this.$router.push("/users")
                this.$toast.success("Successfully updated Administrador (" + this.user.email + ").")
            }).catch(error => {
                this.$toast.error("Could not update Administrador (" + this.user.email + ").<\/br>Error: '" + error.response.data + "'")
            })
        },
    },
    created() {
        this.$axios.$get(`/api/administradores/${this.$route.params.email}`).then(user => {
            this.user = user
            this.userResetName = user.name
        }).catch(error => console.log(error))
    }
}
</script>
