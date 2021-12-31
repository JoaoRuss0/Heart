<template>
    <UpdateProfissionalAdministrador :user="user" :resetname="userResetName" @update="onSubmit"/>
</template>

<script>
import UpdateProfissionalAdministrador from "../../../components/UpdateProfissionalAdministrador";
import administradorRoutes from "../../../middleware/administradorRoutes";
export default {
    components: {UpdateProfissionalAdministrador},
    middleware: ["administradorRoutes"],
    data() {
        return {
            user: null,
            userResetName: ""
        }
    },
    methods:{
        onSubmit() {
            this.$axios.$put("/api/profissionaisdesaude/" + this.$route.params.email, this.user).then(response => {
                this.$router.push("/users")
                this.$toast.success("Successfully updated ProfissionalDeSaude (" + this.user.email + ").")
            }).catch(error => {
                this.$toast.error("Could not update ProfissionalDeSaude (" + this.user.email + ").<\/br>Error: '" + error.response.data + "'")
            })
        },
    },
    created() {
        this.$axios.$get(`/api/profissionaisdesaude/${this.$route.params.email}`).then(user => {
            this.user = user
            this.userResetName = user.name
        }).catch(error => console.log(error))
    }
}
</script>
