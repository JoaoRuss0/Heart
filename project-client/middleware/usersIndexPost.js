export default function ({store, $toast, redirect, router}){
    if(store.state.auth.user.groups[0] != "Administrador") {
        $toast.error("Can not access this route.")
        redirect("/")
    }
}
