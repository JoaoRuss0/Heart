<template>
    <div id="app">
        <b-navbar toggleable="lg">
            <b-navbar-brand><nuxt-link class="nav-link" to="/" exact>Heart</nuxt-link></b-navbar-brand>
            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
            <b-collapse id="nav-collapse" is-nav>
                <b-navbar-nav>
                    <template v-if="$store.state.auth.loggedIn == true">
                        <template v-if="$store.state.auth.user.groups[0] == 'Administrador'">
                            <li class="nav-item">
                                <nuxt-link class="nav-link" to="/users">Users</nuxt-link>
                            </li>
                        </template>
                        <template v-if="$store.state.auth.user.groups[0] == 'ProfissionalDeSaude'">
                            <li class="nav-item">
                                <nuxt-link class="nav-link" to="/doentes">Doentes</nuxt-link>
                            </li>
                        </template>
                        <template v-if="$store.state.auth.user.groups[0] != 'Doente'">
                            <li class="nav-item">
                                <nuxt-link class="nav-link" to="/dadosbiomedicos">Dados Biomedicos</nuxt-link>
                            </li>
                        </template>
                        <li class="nav-item">
                            <nuxt-link class="nav-link" to="/observacoes">Observações</nuxt-link>
                        </li>
                        <li class="nav-item">
                            <nuxt-link class="nav-link" to="/prescricoes">Prescrições</nuxt-link>
                        </li>
                    </template>
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
    name: "default",
    methods: {
        signOut() {
            this.$auth.logout()
            this.$router.push("/")
        }
    }
}
</script>

<style>
a.nuxt-link-active {
    font-weight: 500;
}

a.nuxt-link-exact-active {
    font-weight: 600;
}
</style>
