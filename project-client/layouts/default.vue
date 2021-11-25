<template>
    <div id="app">
        <b-navbar toggleable="lg">
            <b-navbar-brand href="/">Heart</b-navbar-brand>
            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
            <b-collapse id="nav-collapse" is-nav>
                <b-navbar-nav>
                    <li class="nav-item">
                        <nuxt-link class="nav-link" to="/users">Users</nuxt-link>
                    </li>
                </b-navbar-nav>
                <b-navbar-nav class="ml-auto">
                    <b-nav-item-dropdown v-if="$auth.loggedIn" right>
                        <template #button-content>
                            <em>{{ $auth.user.sub }}</em>
                        </template>
                        <b-dropdown-item @click.prevent="signOut">Sign Out</b-dropdown-item>
                    </b-nav-item-dropdown>
                    <li class="nav-item" v-else>
                        <nuxt-link class="nav-link" to="/auth/login">Sign In</nuxt-link>
                    </li>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>
        <main>
            <Nuxt/>
            <b-container class="mt-1">
                <div class="text-right">
                    <b-button class="px-4" @click="$router.back()">
                        <b-icon-arrow-left></b-icon-arrow-left>
                    </b-button>
                </div>
            </b-container>
        </main>
    </div>
</template>
<script>
export default {
    methods: {
        signOut() {
            this.$auth.logout()
            this.$router.push("/")
        }
    }
}
</script>
