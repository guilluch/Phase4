#include <iostream>
#include <fstream>
#include <vector>
#include <cstdint>


using namespace std;

vector <string> lines;
vector <string> words;
vector <string> motsSansDoublons;
vector <unsigned> entiers;

size_t find(string str, vector <string> tab, unsigned pos = 0) {
    for (unsigned i = pos ; i < tab.size() ; ++i) {
        if (tab[i] == str) return i;
    }
    return tab.size();
}

string getText(string filePath) {
    ifstream file(filePath , ios::in);
    string line, text;
    if (!file) throw(404);
    for (; getline(file, line) ;) {
        text += line + '\n';

    }
    return text;
}

vector <string> getFile(string filePath) {
    ifstream file(filePath , ios::in);
    string line;
    vector <string> tab;
    if (!file) throw(404);
    for (; getline(file, line) ;) {
        tab.push_back(line);
    }
    return tab;
}


void createFile(string oFilePath, string text) {
    ofstream file(oFilePath, ios::out);
    file << text;
    cout << oFilePath << " has been generated" << endl;
}


string creerDico() {
    string dico;
    for (unsigned i = 0 ; i < motsSansDoublons.size() ; ++i) {
        dico += motsSansDoublons[i] + ';';
    }
    return dico;
}


string traitement(string str) {
    string text = str;
    size_t pos = 0;
    //string mot = "";
    /*for (unsigned i = 0 ; i < 10 ; ++i) {
        while (true) {
            mot = lines[i].substr(pos, lines[i].find("\"",pos+1) - pos);
            if (find(mot, words) == words.size()) {
                motsSansDoublons.push_back(mot);
            }
            words.push_back(mot);
            pos = lines[i].find(";",pos+1);
            if (pos == string::npos) break;
            pos += 2;
        }
        words.push_back(";");
        pos = 0;
    }

    for (unsigned i = 0 ; i < words.size() ; ++i) {
        if (words[i] == ";") {
            entiers.push_back(0);
        } else {
            entiers.push_back(find(words[i],motsSansDoublons)+1);
        }
    }
    for (unsigned i = 0 ; i < entiers.size() ; ++i) {
        if (entiers[i] == 0) {
            text += '\n';
        } else {
            text += to_string(entiers[i]) + ' ';
        }
    }*/

    //motsSansDoublons = lines;
    cout << text.size() << endl;
    for (unsigned i = 0 ; i < motsSansDoublons.size() ; ++i) {
        motsSansDoublons[i].replace(motsSansDoublons[i].find("\r"), 1, "");
    }
    while (text.find(' ', 0) != string::npos) {
        text.replace(text.find(' ', 0), 1 , ";");
    }
    while (text.find(";\r", 0) != string::npos) {
        text.replace(text.find(";\r", 0), 2 , "");
    }

    text.replace(pos, 1, "\"" + motsSansDoublons[0] + "\"");
    for (unsigned i = 0 ; i < motsSansDoublons.size() ; ++i) {
        while (true) {
            string str2 = "\n" + to_string(i+1) + ";";
            pos = text.find(str2, 0);
            if (pos == string::npos) {
                str2 = ";" + to_string(i+1) + "\n";
                pos = text.find(str2, 0);
                if (pos == string::npos) {
                    str2 = ";" + to_string(i+1) + ";";
                    pos = text.find(str2, 0);
                    if (pos == string::npos)
                        break;
                }
            }
            text.replace(pos+1, str2.size()-2, "\"" + motsSansDoublons[i] + "\"");
            //cout << pos << endl;
        }
        cout << i << '/' << motsSansDoublons.size() << endl;
    }
    return text;
}

string traitementRegleAsso(string str) {
    string text = "";
    size_t pos = 0;

    //On enleve les retours chariot dans motsSansDoublons
    for (unsigned i = 0 ; i < motsSansDoublons.size() ; ++i) {
        if (motsSansDoublons[i].find("\r") != string::npos)
            motsSansDoublons[i].replace(motsSansDoublons[i].find("\r"), 1, "");
    }

    for (unsigned i = 0 ; i < lines.size() ; ++i) {
        if (lines[i].find(',') > 10) {
            lines[i].replace(pos+1, lines[i].find(']')- 1, "\"" + motsSansDoublons[stoi(lines[i].substr(pos+1, lines[i].find(']', pos)-1))] + "\"");
            pos = lines[i].find('[', pos+3) - 2;
        } else {
            lines[i].replace(pos+1, lines[i].find(',')- 1, "\"" + motsSansDoublons[stoi(lines[i].substr(pos+1, lines[i].find(',', pos)-1))] + "\"");
            pos = lines[i].rfind('"');
        }
        while (lines[i].find(']'), pos+3) {
            if (lines[i].find(',', pos+3) < lines[i].find(']', pos)) {
                lines[i].replace(pos+3, lines[i].find(',', pos+3)- pos - 3, "\"" + motsSansDoublons[stoi(lines[i].substr(pos+3, lines[i].find(',', pos+3)- pos-3))] + "\"");
                pos = lines[i].rfind('"');
            } else if (lines[i].find(',', pos+3) > lines[i].find(']', pos)) {
                lines[i].replace(pos+3, lines[i].find(']', pos+3)- pos - 3, "\"" + motsSansDoublons[stoi(lines[i].substr(pos+3, lines[i].find(']', pos)-pos-3))] + "\"");
                pos = lines[i].find('[', pos+3) - 2;
            }
        }
        pos = 0;
        cout << i << endl;
    }




    for (unsigned i = 0 ; i < lines.size() ; ++i) {
        text += lines[i] + '\n';
    }
    return text;
}

int main(int argc, char *argv[]) {
    cout << argv[1] << endl;
    string text = getText(argv[1]);
    lines = getFile(argv[1]);
    //string dicoPath = argv[1];
    //string csvPath = argv[1];
    string dicoPath = "dico.txt";
    //string csvPath = "science-en.csv";
    string regleDasso = "../regleDassoTextuel.txt";
    motsSansDoublons = getFile(dicoPath);
    //cout << lines.size() << endl;
    //cout << motsSansDoublons.size() << endl;
    text = traitementRegleAsso(text);
    createFile(regleDasso, text);
    return 0;
}
