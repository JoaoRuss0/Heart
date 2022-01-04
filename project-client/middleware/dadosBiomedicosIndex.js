export default function ({store, $toast, redirect, router}){
    if(store.state.auth.user.groups[0] == "Doente") {
        $toast.error("Can not access this route.")
        redirect("/")
    }
}
