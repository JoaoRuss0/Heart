<template>
    <b-container>
        <h3>Login into Heart</h3>
        <b-form @submit.prevent="onSubmit" @reset.prevent="onReset" :disabled="!isFormValid">
            <b-form-group
                id="inputEmailGroup"
                label="Email"
                label-for="inputEmail"
                :invalid-feedback="invalidFeedbackEmail"
                :state="stateEmail"
            >
                <b-input
                    id="inputEmail"
                    ref="email"
                    name="email"
                    type="email"
                    placeholder="Your email address"
                    v-model.trim="email"
                    :state="stateEmail"
                    required/>
            </b-form-group>
            <b-form-group
                id="inputPasswordGroup"
                label="Password"
                label-for="inputPassword"
                :invalid-feedback="invalidFeedbackPassword"
                :state="statePassword"
            >
                <b-input
                    id="inputPassword"
                    name="password"
                    type="password"
                    placeholder="Your password"
                    v-model="password"
                    :state="statePassword"
                    required/>
            </b-form-group>
            <b-button type="reset" class="btn-warning">Reset</b-button>
            <b-button type="submit" class="btn-success">Submit</b-button>
        </b-form>
    </b-container>
</template>

<script>
export default {
    auth: 'guest',
    data() {
        return {
            email: null,
            password: null
        }
    },
    computed: {
        stateEmail() {
            if(!this.email)
            {
                return null
            }
            return this.$refs.email.checkValidity();
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

                if (this.$auth.user.groups.includes('Administrador')) {
                    this.$router.push('/users')
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
