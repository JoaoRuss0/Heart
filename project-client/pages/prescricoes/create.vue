<template>
    <b-container>
        <h3>Passar Prescrição</h3>
        <b-form @submit.prevent="onSubmit" @reset.prevent="onReset" :disabled="!isFormValid">
            <b-form-group
                id="inputEmailGroup"
                label="Causa"
                label-for="inputCausa"
                :invalid-feedback="invalidFeedbackEmail"
                :state="stateEmail"
            >
                <b-input
                    id="inputCausa"
                    ref="email"
                    name="email"
                    type="email"
                    placeholder="A causa da prescrição"
                    v-model.trim="email"
                    :state="stateEmail"
                    required/>
            </b-form-group>
            <b-form-group
                id="inputPasswordGroup"
                label="Email do Doente:"
                label-for="inputPassword"
                :invalid-feedback="invalidFeedbackPassword"
                :state="statePassword"
            >
                <b-input
                    id="inputEmail"
                    name="email"
                    type="email"
                    placeholder="example:bomdiaxau@gmail.com"
                    v-model="email"
                    :state="stateEmail"
                    required/>
            </b-form-group>
            <b-button type="reset" class="btn-warning">Back</b-button>
            <b-button type="submit" class="btn-success">Create</b-button>
        </b-form>
    </b-container>
</template>

<script>
export default {
    auth: false,
    data() {
        return {
            causa: null,
            password: null
        }
    },
    computed: {
        stateEmail() {
            if(!this.email)
            {
                return null
            }
            return this.$refs.email.checkValidity()
        },
        statePassword() {
            if(!this.password)
            {
                return null
            }
            return this.password.length >= 3
        },
        invalidFeedbackEmail() {
            if(this.stateEmail)
            {
                return ""
            }
            return "Not a valid email address."
        },
        invalidFeedbackPassword() {
            if(this.statePassword)
            {
                return ""
            }
            return "Password is too short (at least 3 in length)."
        },
        isFormValid() {
            return (this.stateEmail && this.password)
        }
    },
    methods: {
        onSubmit() {
            this.$auth.loginWith('local', {
                data: {
                    email: this.email,
                    password: this.password
                }
            }).then(() => {
                this.$toast.success('You are logged in!').goAway(3000)

                if (this.$auth.user.groups.includes('Teacher')) {
                    this.$router.push('/students')
                }
            }).catch(() => {
                this.$toast.error('Sorry, you cant login. Ensure your credentials are correct').goAway(3000)
            })
        },
        onReset() {
            this.email = null
            this.password = null
        }
    }
}
</script>
