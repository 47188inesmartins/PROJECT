
export type cookieParams = {
    name : string ,
    value: string ,
    expire: string | undefined,
    path: string | undefined,
};

const TOKEN_PARAM = "token"


export async function fetchGetSession(onSuccess: (token: string, role: string) => void) {
    console.log("fetch get session")
    try {
        const resp = await fetch('/check_session', {  credentials: 'include'})
        const response = await resp.json()
        const token = response.first.find( it.name === TOKEN_PARAM)
        const role = response.second
        onSuccess(token.value, role.value)
    }catch (error){
        console.log(error)
    }
}