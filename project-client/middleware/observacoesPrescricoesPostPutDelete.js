export default function ({store, $toast, redirect, router}){
    if(store.state.auth.user.groups[0] != "ProfissionalDeSaude") {
        $toast.error("Can not access this route.")
        redirect("/dadosbiomedicos")
    }
}
