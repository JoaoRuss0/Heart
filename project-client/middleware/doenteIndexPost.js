export default function (context) {
    if (!(context.store.state.auth.user.groups[0] == "ProfissionalDeSaude")) {
        context.$toast.error("Can not access this route.")
        context.redirect("/")
    }
}
