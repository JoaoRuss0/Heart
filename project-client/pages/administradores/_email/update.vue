<template>
    <UpdateProfissionalAdministrador :user="user" :resetname="userResetName" @update="onSubmit"/>
</template>

<script>
import UpdateProfissionalAdministrador from "../../../components/UpdateProfissionalAdministrador";
import administradoresPut from "../../../middleware/administradoresPut";

export default {
    components: {UpdateProfissionalAdministrador},
    middleware: administradoresPut,
    data() {
        return {
            user: null,
            userResetName: ""
        }
    },
    methods:{
        onSubmit() {
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
