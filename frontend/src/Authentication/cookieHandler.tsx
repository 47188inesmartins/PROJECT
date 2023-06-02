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
                //  mode: 'no-cors',
                credentials: "include",
            }
        )
        console.log("RESPONSEE1",
            await  resp)
        const response = await resp.json()
        console.log("RESPONSEE1",  response.message)
        const token = response.body.first.find(it => it.name === TOKEN_PARAM)
        console.log("TOKENNN", token)
        const role = response.second
        onSuccess(token.value, role.value)
    }catch (error){
        console.log(error)
    }
}