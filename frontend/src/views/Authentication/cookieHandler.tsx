
export async function fetchGetSession(onSuccess: (token: string, role: Array<any>) => void) {
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