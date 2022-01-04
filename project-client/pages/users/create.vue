<template>
    <b-container>
        <h1>
            Create new user
        </h1>
        <b-form @submit.prevent="onSubmit" @reset.prevent="onReset" :disabled="!isFormValid">
            <b-form-group
                id="fieldset-user-type"
                label="User Type:"
                label-for="inputUserType"
                >
                <b-form-radio-group id="inputUserType" v-model="user.tipo" :options="options" />
            </b-form-group>
            <b-form-group
                id="fieldset-name"
                label="Name:"
                label-for="inputName"
                valid-feedback="Done!"
                :invalid-feedback="invalidFeedbackName"
                :state="stateName"
            >
                <b-form-input
                    id="inputName"
                    v-model="user.name"
                    placeholder="Enter a name"
                    :state="stateName"
                    required
                ></b-form-input>
            </b-form-group>
            <b-form-group
                id="fieldset-email"
                label="Email:"
                label-for="inputEmail"
                valid-feedback="Done!"
                :invalid-feedback="invalidFeedbackEmail"
                :state="stateEmail"
            >
                <b-form-input
                    id="inputEmail"
                    type="email"
                    ref="email"
                    v-model="user.email"
                    placeholder="Enter an email address"
                    :state="stateEmail"
                    required
                ></b-form-input>
            </b-form-group>
            <b-form-group
                id="fieldset-password"
                label="Password:"
                label-for="inputPassword"
                valid-feedback="Done!"
                :invalid-feedback="invalidFeedbackPassword"
                :state="statePassword"
            >
                <b-input-group>
                    <b-form-input
                        id="inputPassword"
                        type="password"
                        v-model="user.password"
                        placeholder="Enter a password"
                        :state="statePassword"
                        required
                    ></b-form-input>
                    <b-input-group-append is-text @change="togglePasswordVisibility">
                        <b-form-checkbox switch class="mr-n2">
                        </b-form-checkbox>
                    </b-input-group-append>
                </b-input-group>
            </b-form-group>
            <div class="text-right">
                <b-button variant="primary" type="submit" :disabled="!isFormValid">Create</b-button>
                <b-button variant="danger" type="reset" :disabled="fieldsAreEmpty">Reset</b-button>
            </div>
        </b-form>
    </b-container>
</template>

<script>
export default {
    data() {
        return {
            options: [
                {text: "Administrator", value:"Administrador"},
                {text: "Health Professional", value:"ProfissionalDeSaude"}
            ],
            user: {
                tipo: "Administrador",
                name: null,
                email: null,
                password: null
            }
        }
    },
    computed: {
        stateName() {
            if(!this.user.name) {
                return null
            }
            return this.user.name.length >= 3
        },
        invalidFeedbackName() {
            if(!this.stateName) {
                return "Name has to be at least 3 in length."
            }
            return ""
        },
        stateEmail() {
            if(!this.user.email) {
                return null
            }
            return this.$refs.email.checkValidity()
        },
        invalidFeedbackEmail() {
            if(!this.stateName) {
                return "Email has to have a valid email address format."
            }
            return ""
        },
        statePassword() {
            if(!this.user.password) {
                return null
            }
            return this.user.password.length >= 8
        },
        invalidFeedbackPassword() {
            if(!this.statePassword) {
                return "Password has to be at least 8 in length."
            }
            return ""
        },
        isFormValid() {
            return this.stateName && this.stateEmail && this.statePassword;
        },
        fieldsAreEmpty() {
            return !this.user.name && !this.user.email && !this.user.password
        }
    },
    methods: {
        togglePasswordVisibility() {
            var element = document.querySelector('#inputPassword')

            if(element.attributes.getNamedItem('type').value == "password") {
                element.setAttribute('type', 'text')
            }else {
                element.setAttribute('type', 'password')
            }
        },
        onSubmit() {
            this.$axios.$post("/api/" + ((this.user.tipo == "Administrador") ? "administradores/" : "profissionaisdesaude/"), this.user).then(response => {
                this.$router.push("/users")
                this.$toast.success("Successfully created new " + this.user.tipo + " (" + this.user.email + ").")
            }).catch(error => {
                this.$toast.error("Could not create new " + this.user.tipo + ".<\/br>Error: '" + error.response.data + "'")
            })
        },
        onReset() {
            this.user.name = null
            this.user.email = null
            this.user.password = null
        }
    }
}
</script>
