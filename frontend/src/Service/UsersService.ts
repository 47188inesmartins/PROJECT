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

    export function profilePicture(id:String,selectedFile){
        try {
            const formData = new FormData();
            formData.append('file', selectedFile);
            //UsersService.profilePicture(params,formData)
            const response = fetch(`http://localhost:8000/api/user/${id}/profile-pic`, {
                method: 'POST',
                body: formData
            }).then(response =>{
                    if (response.status === 200) {
                        alert("Your image was receive");
                        window.location.href = '/';
                        console.log('Upload concluído', response);
                    } else {
                        console.error('Error during the upload,try again:', response.status);
                    }
                }
            )
        } catch (error) {
            console.error('Erro durante o upload:', error);
        }
    }

    export function getUser(){
        const token =  Cookies.get('name')
        fetch(`/api/user/id-info`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
        }).then(async response =>{
                if (response.status === 200) {
                    const resp = await response.json()
                    window.location.href = `/user/${resp}/upload-pic`;
                } else {
                    console.error('Something went wrong:', response.status);
                }
            }
        )
    }


    export function login(userCredentials) {
        fetch(`/api/user/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(userCredentials),
        })
        .then(async response => {
            const resp = await response.json()
            if (resp.status === 200) {
                Cookies.set('name', resp.first, {expires: 7});
                window.location.href = '/'
            } else if (resp.status === 400) {
                alert("Try again")
            }
        })
        .catch(error => {
            console.error('Ocorreu um erro:', error);
        });
    }


    export function signup(userCredentials) {
        fetch(`/api/user`, {
            method: 'POST',
            body: JSON.stringify(userCredentials),
            headers: {
                'Content-Type': 'application/json',
            }
        })
            .then(async response => {
                const resp = await response.json()
                if(resp.status === 201){
                    alert("verify your account")
                    window.location.href = '/'
                } else if (resp.status === 400) {
                    alert("Try again")
                }
            })
            .catch(error => {
                window.alert(error)
                console.error('An error has occurred:', error);
            });
    }


}