import Cookies from 'js-cookie';


export namespace ServiceServices{

    export function addServices(cid,requestData){
        const token =  Cookies.get('name');
        console.log(requestData)
        fetch(`/api/service/company/${cid}`,{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token} `
            },
            body: JSON.stringify(requestData)
        }).then(response => {
                if(response.status == 400){
                    window.alert("An error as occurred!")
                    //window.location.href =`/company/${cid}/services`;
                }else if(response.status == 201) {
                    window.alert("You added an service")
                    window.location.href = `/company/${cid}/profile`;
                }else if(response.status == 403){
                    window.alert("You can't make this action")
                }
            }
        )
    }
}