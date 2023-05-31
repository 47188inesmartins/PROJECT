import {Fetch} from "../useFetch";

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
        const resp = await fetch('http://localhost:8080/user/check-session',
       {
          //  mode: 'no-cors',
            credentials: "include",
        }
        )
        console.log("RESPONSEE1", await  resp)
        const response = await resp.json()
        console.log("RESPONSEE1",  response)
        const token = response.body.first.find( it.name === TOKEN_PARAM)
        console.log("TOKENNN", token)
        const role = response.second
        onSuccess(token.value, role.value)
    }catch (error){
        console.log(error)
    }
}