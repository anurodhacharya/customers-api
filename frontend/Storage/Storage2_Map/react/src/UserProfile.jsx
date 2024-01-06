const UserProfile = ({ name, age, gender, ...props }) => {
    // const { name, age, gender } = props;
    return (
        <div>
            <h1>{ name }</h1>
            <p>{ age }</p>
            <img src={`https://randomuser.me/api/portraits/${gender}/75.jpg`}></img>
            { props.children }
        </div>
    )
}

export default UserProfile;
/*
const UserProfile = (props) => {
    return (
        <div>
            <h1>{ props.name }</h1>
            <p>{ props.age }</p>
            <img src={`https://randomuser.me/api/portraits/${props.gender}/75.jpg`}></img>
        </div>
    )
}

export default UserProfile;*/
