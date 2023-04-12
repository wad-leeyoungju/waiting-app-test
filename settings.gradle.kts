rootProject.name = "mimic-waiting-project"
include("waiting-api")
include("waiting-data")
include("waiting-shared")
include("client")
include("client:client-pos")
findProject(":client:client-pos")?.name = "client-pos"
include("waiting-gateway")
include("waiting-shared:waiting-event-handler")
findProject(":waiting-shared:waiting-event-handler")?.name = "waiting-event-handler"
include("waiting-event-handler")
