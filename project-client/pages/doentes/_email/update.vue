<template>
    <b-container>
        <h1>
            Update doente:
        </h1>
        <b-form @submit.prevent="onSubmit" @reset.prevent="onReset" :disabled="!isFormValid">
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
                    v-model="doente.name"
                    placeholder="Enter a name"
                    :state="stateName"
                    required
                ></b-form-input>
            </b-form-group>
            <b-form-group
                id="fieldset-email"
                label="Email:"
                label-for="inputEmail"
            >
                <b-form-input
                    id="inputEmail"
                    type="email"
                    v-model="doente.email"
                    disabled
                    required
                ></b-form-input>
            </b-form-group>
            <b-form-group
                id="fieldset-current-password"
                label="Current Password:"
                label-for="inputCurrentPassword"
                valid-feedback="Done!"
                :invalid-feedback="invalidFeedbackCurrentPassword"
                :state="stateCurrentPassword"
            >
                <b-input-group>
                    <b-form-input
                        id="inputCurrentPassword"
                        type="password"
                        v-model="currentpassword"
                        placeholder="Enter the doente's current password"
                        :state="stateCurrentPassword"
                        :required="!(currentpassword == null || currentpassword.length == 0)"
                    ></b-form-input>
                    <b-input-group-append is-text @change="toggleCurrentPasswordVisibility">
                        <b-form-checkbox switch class="mr-n2">
                        </b-form-checkbox>
                    </b-input-group-append>
                </b-input-group>
            </b-form-group>
            <b-form-group
                id="fieldset-new-password"
                label="New Password:"
                label-for="inputNewPassword"
                valid-feedback="Done!"
                :invalid-feedback="invalidFeedbackNewPassword"
                :state="stateNewPassword"
            >
                <b-input-group>
                    <b-form-input
                        id="inputNewPassword"
                        type="password"
                        v-model="newpassword"
                        placeholder="Enter the new password"
                        :state="stateNewPassword"
                        :disabled="newPasswordDisabled"
                        :required="!(currentpassword == null || currentpassword.length == 0)"
                    ></b-form-input>
                    <b-input-group-append is-text @change="toggleNewPasswordVisibility">
                        <b-form-checkbox switch class="mr-n2">
                        </b-form-checkbox>
                    </b-input-group-append>
                </b-input-group>
            </b-form-group>
            <div class="text-right">
                <b-button variant="warning" type="submit" :disabled="!isFormValid">Update</b-button>
                <b-button variant="danger" type="reset" :disabled="fieldsAreEmpty">Reset</b-button>
            </div>
        </b-form>
    </b-container>
</template>

<script>
import doentePut from "../../../middleware/doentePut";

export default {
    middleware: doentePut,
    data() {
        return {
            currentpassword: null,
            newpassword: null,
            doente: {
                email: null,
                name: null,
            }
        }
    },
    created() {
        this.$axios.$get('/api/doentes/' + this.$route.params.email).then(doente => {
            this.doente = doente
        }).catch(error => {
            this.$toast.error("Could not get doente (" + this.$route.params.email + ")." )
            this.$router.push("/doentes")
        })
    },
    computed: {
        stateName() {
            if(!this.doente.name) {
                return null
            }
            return this.doente.name.length >= 3
        },
        invalidFeedbackName() {
            if(!this.stateName) {
                return "Name has to be at least 3 in length."
            }
            return ""
        },
        stateCurrentPassword() {
            if(!this.currentpassword) {
                return null
            }
            return this.currentpassword.length >= 8
        },
        invalidFeedbackCurrentPassword() {
            if(!this.stateCurrentPassword) {
                return "Current password has to be at least 8 in length."
            }
            return ""
        },
        stateNewPassword() {
            if(!this.newpassword) {
                return null
            }
            return this.newpassword.length >= 8
        },
        invalidFeedbackNewPassword() {
            if(!this.stateNewPassword) {
                return "New password has to be at least 8 in length."
            }
            return ""
        },
        isFormValid() {
            if(this.stateCurrentPassword == null) {
                return this.stateName;
            }
            return this.stateName && this.stateNewPassword && this.stateCurrentPassword;
        },
        fieldsAreEmpty() {
            return !this.doente.name && !this.currentpassword && !this.newpassword
        },
        newPasswordDisabled(){
            if(this.currentpassword == null || this.currentpassword.length == 0){
                this.newpassword = null
            }
            return this.currentpassword == null || this.currentpassword.length == 0
        }
    },
    methods: {
        toggleCurrentPasswordVisibility() {
            var element = document.querySelector('#inputCurrentPassword')

            if(element.attributes.getNamedItem('type').value == "password") {
                element.setAttribute('type', 'text')
            }else {
                element.setAttribute('type', 'password')
            }
        },
        toggleNewPasswordVisibility() {
            var element = document.querySelector('#inputNewPassword')

            if(element.attributes.getNamedItem('type').value == "password") {
                element.setAttribute('type', 'text')
            }else {
                element.setAttribute('type', 'password')
            }
        },
        onSubmit() {
            this.doente.currentpassword= this.currentpassword
            this.doente.newpassword = this.newpassword

            this.$axios.$put("/api/doentes/" + this.$route.params.email, this.doente).then(response => {
                this.$router.push("/doentes")
                this.$toast.success("Successfully updated doente (" + this.doente.email + ").")
            }).catch(error => {
                this.$toast.error("Could not update doente.<\/br>Error: '" + error.response.data + "'")
            })
        },
        onReset() {
            this.doente.name = null
            this.currentpassword = null
            this.newpassword = null
        }
    }
}
</script>
