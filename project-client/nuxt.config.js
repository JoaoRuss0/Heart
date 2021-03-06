export default {
    // Global page headers: https://go.nuxtjs.dev/config-head
    head: {
        title: 'project-client',
        htmlAttrs: {
            lang: 'en'
        },
        meta: [
            {charset: 'utf-8'},
            {name: 'viewport', content: 'width=device-width, initial-scale=1'},
            {hid: 'description', name: 'description', content: ''},
            {name: 'format-detection', content: 'telephone=no'}
        ],
        link: [
            {rel: 'icon', type: 'image/x-icon', href: '/favicon.ico'}
        ]
    },

    // Global CSS: https://go.nuxtjs.dev/config-css
    css: [],

    // Plugins to run before rendering page: https://go.nuxtjs.dev/config-plugins
    plugins: [],

    // Auto import components: https://go.nuxtjs.dev/config-components
    components: true,

    // Modules for dev and build (recommended): https://go.nuxtjs.dev/config-modules
    buildModules: [],

    // Modules: https://go.nuxtjs.dev/config-modules
    modules: [
        // https://go.nuxtjs.dev/bootstrap
        'bootstrap-vue/nuxt',
        // https://go.nuxtjs.dev/axios
        '@nuxtjs/axios',
        '@nuxtjs/auth',
        '@nuxtjs/toast'
    ],

    bootstrapVue: {
        icons: true
    },

    // Axios module configuration: https://go.nuxtjs.dev/config-axios
    axios: {
        proxy: true,
        credentials: true
    },

    proxy: {
        '/api/': {
            target: 'http://localhost:8080/project/api/',
            pathRewrite: {
                '^/api/': ''
            }
        }
    },

    // Build Configuration: https://go.nuxtjs.dev/config-build
    build: {},

    // Disable Server Side rendering
    ssr: false,

    // Auth module Configuration: https://auth.nuxtjs.org
    auth: {
        redirect: {
            login: '/auth/login',
            logout: '/',
            home: '/'
        },
        watchLoggedIn: true,
        strategies: {
            local: {
                endpoints: {
                    login: {
                        url: '/api/auth/login',
                        method: 'post',
                        propertyName: 'token'
                    },
                    logout: false,
                    user: {
                        url: '/api/auth/user',
                        method: 'get',
                        propertyName: ''
                    }
                },
                // tokenRequired: true,
                // tokenType: 'bearer'
            }
        }
    },

    router: {
        middleware: [
            'auth'
        ]
    },

    toast: {
        duration: 5000,
    }
}
