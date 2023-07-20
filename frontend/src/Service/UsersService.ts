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
            .then(async response => {
                console.log("AQUI",response)
                if(response.status === 200){
                    alert("Employee has been removed from your company")
                    window.location.href = `/company/${cid}/managing/employees`;
                }else if(response.status === 400){
                    alert("Employee has been removed")
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
        const token =  Cookies.get('name')
        fetch(`/api/company/${id}/employee`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(body)
        }).then( response => {
                if (response.status === 200) {
                    alert("Your employee was added");
                    window.location.href = `/company/${id}/managing/employees`;
                    console.log('Your image is save', response);
                } else if(response.status === 400){
                    alert("The employee wasn't added");
                    window.location.href = `/company/${id}/managing/employees`;
                    console.error('Something went wrong:', response.status);
                }else if(response.status === 404){
                    alert("The employee wasn't added");
                    console.error('Something went wrong:', response.status);
                }
            }
        )
    }

    export function profilePicture(id:String,formData:FormData){
        const token =  Cookies.get('name')
        fetch(`/api/user/profile-pic`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: formData
        }).then( response =>{
                if (response.status === 200) {
                    alert("Your menssage was sent");
                    window.location.href = '/';
                    console.log('Your image is save', response);
                } else {
                    console.error('Something went wrong:', response.status);
                }
            }
        )
    }
}