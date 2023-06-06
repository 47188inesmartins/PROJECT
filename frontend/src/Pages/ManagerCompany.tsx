import * as React from 'react';
import {useParams} from "react-router-dom";
import {Fetch} from "../Utils/useFetch";
/*
import {
    ScheduleComponent, ResourcesDirective, ResourceDirective, ViewsDirective, ViewDirective,
    ResourceDetails, Inject, TimelineViews, Resize, DragAndDrop, TimelineMonth, Day
} from '@syncfusion/ej2-react-schedule';
*/
export function MyCompany() {
    const a = useParams()
    const res = Fetch(`/company/${a.id}`,'GET')
    return(
        <div>
                {!res.response?
                    <div className="loading">
                        <div className="spinner-border" role="status">
                            <span className="sr-only">Loading...</span>
                        </div>
                    </div>
                        :<>
                            <h1>{res.response.name}</h1>
                        </>
                }
        </div>
    );
}

/*
/**
 * schedule block events sample
 */
/*
function BlockEvents() {
    const dataSource = require('./datasource.json');
    const data: Record<string, any>[] = dataSource.blockData;
    //const data: Record<string, any>[] = extend([], (dataSource as Record<string, any>).blockData, null, true) as Record<string, any>[];
    const employeeData: Record<string, any>[] = [
        { Text: 'Alice', Id: 1, GroupId: 1, Color: '#bbdc00', Designation: 'Content writer' },
        { Text: 'Nancy', Id: 2, GroupId: 2, Color: '#9e5fff', Designation: 'Designer' },
        { Text: 'Robert', Id: 3, GroupId: 1, Color: '#bbdc00', Designation: 'Software Engineer' },
        { Text: 'Robson', Id: 4, GroupId: 2, Color: '#9e5fff', Designation: 'Support Engineer' },
        { Text: 'Laura', Id: 5, GroupId: 1, Color: '#bbdc00', Designation: 'Human Resource' },
        { Text: 'Margaret', Id: 6, GroupId: 2, Color: '#9e5fff', Designation: 'Content Analyst' }
    ];

    function getEmployeeName(value: ResourceDetails): string {
        return (value as ResourceDetails).resourceData[(value as ResourceDetails).resource.textField] as string;
    }

    function getEmployeeImage(value: ResourceDetails): string {
        return getEmployeeName(value).toLowerCase();
    }

    function getEmployeeDesignation(value: ResourceDetails): string {
        return (value as ResourceDetails).resourceData.Designation as string;
    }

    function resourceHeaderTemplate(props: any): JSX.Element {
        return (<div className="template-wrap"><div className="employee-category"><div className={"employee-image " + getEmployeeImage(props)}></div><div className="employee-name">
            {getEmployeeName(props)}</div><div className="employee-designation">{getEmployeeDesignation(props)}</div></div></div>);
    }

    return (
        <div className='schedule-control-section'>
            <div className='col-lg-12 control-section'>
                <div className='control-wrapper drag-sample-wrapper'>
                    <div className="schedule-container">
                        <ScheduleComponent cssClass='block-events' width='100%' height='650px' selectedDate={new Date(2021, 7, 2)}
                                           currentView='TimelineDay' resourceHeaderTemplate={resourceHeaderTemplate.bind(this)}
                                           eventSettings={{ dataSource: data }} group={{ enableCompactView: false, resources: ['Employee'] }}>
                            <ResourcesDirective>
                                <ResourceDirective field='EmployeeId' title='Employees' name='Employee' allowMultiple={true}
                                                   dataSource={employeeData} textField='Text' idField='Id' colorField='Color'>
                                </ResourceDirective>
                            </ResourcesDirective>
                            <ViewsDirective>
                                <ViewDirective option='Day' />
                                <ViewDirective option='TimelineDay' />
                                <ViewDirective option='TimelineMonth' />
                            </ViewsDirective>
                            <Inject services={[Day, TimelineViews, TimelineMonth, Resize, DragAndDrop]} />
                        </ScheduleComponent>
                    </div>
                </div>
            </div>
        </div>
    );
}
export default BlockEvents;

/*
/**
 * Schedule inline editing sample
 */
/*
function InlineEditing() {
    let scheduleObj: ScheduleComponent;
    const a = fetch('./datasource.json')
        .then(response => response.json())
        .then(data => {
            // Use o objeto de dados aqui
            console.log(data);
        })
        .catch(error => {
            // Trate erros
            console.error('Ocorreu um erro ao carregar o arquivo JSON:', error);
        });
    const resourceData = (a as Record<string, any>).resourceData;
    const timelineResourceData = (a as Record<string, any>).timelineResourceData;
    const data: Record<string, any>[] = [...resourceData, ...timelineResourceData];    const workDays: number[] = [0, 1, 2, 3, 4, 5];
    const categoriesData: Record<string, any>[] = [
        { text: 'Nancy', id: 1, groupId: 1, color: '#df5286' },
        { text: 'Steven', id: 2, groupId: 1, color: '#7fa900' },
        { text: 'Robert', id: 3, groupId: 2, color: '#ea7a57' },
        { text: 'Smith', id: 4, groupId: 2, color: '#5978ee' },
        { text: 'Michael', id: 5, groupId: 3, color: '#df5286' }
    ];


    return (
        <div className='schedule-control-section'>
            <div className='col-lg-12 control-section'>
                <div className='control-wrapper'>
                    <ScheduleComponent width='100%' height='650px' ref={t => scheduleObj = t} cssClass='inline-edit' workDays={workDays} currentView='TimelineWeek' allowInline={true} selectedDate={new Date(2023, 0, 4)}
                                       eventSettings={{ dataSource: data }} group={{ resources: ['Categories'] }} >
                        <ResourcesDirective>
                            <ResourceDirective field='TaskId' title='Category' name='Categories' allowMultiple={true}
                                               dataSource={categoriesData} textField='text' idField='id' colorField='color'>
                            </ResourceDirective>
                        </ResourcesDirective>
                        <ViewsDirective>
                            <ViewDirective option='TimelineWeek' />
                            <ViewDirective option='TimelineMonth' />
                        </ViewsDirective>
                    </ScheduleComponent>
                </div>
            </div>
        </div>
    );
}
*/
