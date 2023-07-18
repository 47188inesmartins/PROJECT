import {Fetch, SimpleFetch1} from "../Utils/useFetch";
import Cookies from 'js-cookie';


export namespace UsersService{

    export function deleteEmployee(cid:string,employeeId:number){
        const token =  Cookies.get('name')
        fetch(`/api/company/${cid}/employees?id=${employeeId}`,{
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        })
            .then(response => {
                console.log("AQUI",response)
                if(response.status === 200){
                    alert("Employee has been removed from your company")
                    window.location.href = `/company/${cid}/managing/employees`;
                }else if(response.status === 400){
                    alert("Something went wrong")
                    window.location.href = `/company/${cid}/managing/employees`;
                }else if(response.status === 403){
                    alert("Forbidden you can´t make this action")
                    window.location.href = `/company/${cid}/managing/employees`;
                }
            })
            .catch(error => {
                console.error('Ocorreu um erro:', error);
            });
    }

    export function profitEmployee(cid:string,employeeId:number){
        const token =  Cookies.get('name')
        fetch(`/company/${cid}/employee-profit/${employeeId}`,{
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        })
            .then(async response => {
                const contentType = response.headers.get('content-type')
                console.log("AQUI",contentType)
                if(response.status === 200){
                    const res = await response.json()
                    console.log("json",res)
                    alert(`Earned money on the last 30 days was ${res}`)
                    window.location.href = `/company/${cid}/managing/employees`;
                }else if(response.status === 400){
                    alert("Try again")
                    window.location.href = `/company/${cid}/managing/employees`;
                }else if(response.status === 403){
                    alert("Forbidden you can´t make this action")
                    window.location.href = `/company/${cid}/managing/employees`;
                }
            })
            .catch(error => {
                console.error('Ocorreu um erro:', error);
            });
    }

    export function addEmployees(id:String,body:any){
        const resp = Fetch(`/company/${id}/employee`, 'POST', body)
        if(resp.response)
            window.location.href = "/"
        return resp
    }

    export function profilePicture(id:String,formData:FormData){
        fetch(`/user/${id}/profile-pic`, {
            method: 'POST',
            body: formData
        }).then(response =>{
            if (response.status === 200) {
                alert("Your image is saved");
                window.location.href = '/';
                console.log('Upload success', response);
            } else {
                console.error('Error during upload:', response.status);
            }
        })
    }
}