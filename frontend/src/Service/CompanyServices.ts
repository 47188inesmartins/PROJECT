import {convertMinutesToHHMMSS} from "../Utils/formater";
import Cookies from 'js-cookie';
import {Fetch} from "../Utils/useFetch";

export namespace CompanyServices{

    export function CreateCompany(selectedValue,companyData,employeeEmails,schedule){
        const token =  Cookies.get('name');
        fetch(`/api/company?duration=${convertMinutesToHHMMSS(selectedValue)}`,{
            method: 'POST',
            headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token} `
            },
            body: JSON.stringify({
                    company: companyData,
                    emails : {
                            emails: employeeEmails
                    },
                    days: schedule.filter(day =>
                        Object.values(day).every(value => value !== '')
                    )
            })
        }).then(async response =>{
            if(response.status == 400){
                    window.alert("Something went wrong")
            }else if(response.status == 201){
                    const resp = await response.json()
                    window.alert("Company created!")
                    window.location.href = `/company/${resp.id}/services`
            }
        })
    }

    export function GetCompanyProfit(id){
            const data = Fetch(`company/${id}/employees-profit`, 'GET');
            console.log("dadadadada",data)
            if(data.response){
                if(data.response.status === 400){
                    alert("Something went wrong")
                    return null
                }
                if(data.response.status === 401){
                    alert("Unauthorized")
                    return null
                }
                if(data.response.status === 403){
                    alert("You can't acess this resource")
                    return null
                }
            }
                return data
    }

    export function GetCompany(id){
        const token =  Cookies.get('name');
        fetch(`/api/company/${id}`,{
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token} `
            }
        }).then(async response =>{
            if(response.status === 400){
                window.alert("Something went wrong")
                return undefined
            }else if(response.status === 200){
                const resp = await response.json()
                console.log("DEBUG",resp)
                return resp
            }
        })
        return undefined
    }
}