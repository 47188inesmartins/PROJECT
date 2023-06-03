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
        const resp = await fetch('/api/user/check-session',
            {
                credentials: "include",
            }
        )
        const response = await resp.json()
        const token = response.first
        const role = response.second
        console.log("token =", token, " role = ", role)
        onSuccess(token, role)
    }catch (error){
        console.log(error)
    }
}