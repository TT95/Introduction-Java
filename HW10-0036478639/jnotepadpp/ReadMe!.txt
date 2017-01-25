Pazi, nakon obavljanja maven ciljeva program vise nece biti pokretjiv iz eclipse!

Neznam zasto, valjda je problem u pom.xml, nakon izvodenja tih ciljeva eclipse dobije u project
direktoriju jos dodatna dva folder src i test (dok zapravo nepostoji viska foldera kada se gleda bas
u direktorij putem OS-a) pa onda onaj bundle pocne gledat direktorij putem tih 'parazitskih' foldera
i pocnu bacat exceptione. Moze se popraviti tako da se napravi mvn clean i onda refresha project.
Ako je netko imao slican poroblem ili zna u cemu je stvar rado bi da mi napise u recenziji!