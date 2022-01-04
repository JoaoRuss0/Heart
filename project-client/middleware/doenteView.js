export default function (context) {
    if (!(context.store.state.auth.user.groups[0] == "Administrador" || context.store.state.auth.user.groups[0] == "ProfissionalDeSaude" || context.store.state.auth.user.sub == context.route.params.email)) {
        context.$toast.error("Can not access this route.")
        context.redirect("/")
    }
}
