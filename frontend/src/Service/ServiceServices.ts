import Cookies from 'js-cookie';


export namespace ServiceServices{

    function addServices(services,schedule,cid){
        const token =  Cookies.get('name');
        const pairs = services.map((service, index) => ({
            first: service,
            second: schedule[index],
        }));

        const requestData = {
            service:pairs
        }
        fetch(`/api/unavailability/company/${cid}`,{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token} `
            },
            body: JSON.stringify(requestData)
        }).then(response => {
                if(response.status == 400){
                    window.alert("An error as occurred!")
                    window.location.href =`/`
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