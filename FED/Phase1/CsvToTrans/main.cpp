#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
#include <set>
#include <cstdint>
#include <list>


using namespace std;

vector <string> lines;
vector <string> words;
vector <string> motsSansDoublons;
set <string> setMotsSansDoublons;
list <string> listMotsSansDoublons;
vector <unsigned> entiers;

size_t find(string str, vector <string> tab, unsigned pos = 0) {
    for (unsigned i = pos ; i < tab.size() ; ++i) {
        if (tab[i] == str) return i;
    }
    return tab.size();
}


vector <string> unique(vector <string> tab) {
    cout << tab.size() << endl;
    for (unsigned i = 0 ; i < tab.size() ; ++i) {
        for (unsigned j = i+1 ; j < tab.size() ; ++j) {
            if (tab[i] == tab[j])
                tab.erase(tab.begin()+j);
        }
        cout << "Etape 2 : " << i << endl;
    }
    cout << tab.size() << endl;
    return tab;
}


string getFile(string filePath) {
    ifstream file(filePath , ios::in);
    string line, text;
    if (!file) throw(404);
    for (; getline(file, line) ;) {
        if (line[line.size()-1] == ';') {
            line[line.size()-1] = ' ';
        }
        lines.push_back(line);
        //text += line + '\n';

    }
    return text;
}


void createFile(string oFilePath, string text) {
    ofstream file(oFilePath, ios::out);
    file << text;
    cout << oFilePath << " has been generated" << endl;
}


string creerDico() {
    string dico;
    for (unsigned i = 0 ; i < motsSansDoublons.size() ; ++i) {
        dico += motsSansDoublons[i] + '\n';
    }
    return dico;
}


string traitement() {
    string text = "";
    size_t pos = 1;
    string mot = "";
    for (unsigned i = 0 ; i < lines.size() ; ++i) {
        while (true) {
            mot = lines[i].substr(pos, lines[i].find("\"",pos) - pos);
            //listMotsSansDoublons.push_back(mot);
            if (find(mot, motsSansDoublons) == motsSansDoublons.size())
                motsSansDoublons.push_back(mot);

            words.push_back(mot);
            entiers.push_back(0);
            pos = lines[i].find(";",pos+1);
            if (pos == string::npos) break;
            pos += 2;

        }
        cout << "Etape 1 : " << i << endl;
        words.push_back(";");
        entiers.push_back(0);
        pos = 1;
    }

    //motsSansDoublons = unique(motsSansDoublons);

    for (unsigned i = 0 ; i < motsSansDoublons.size() ; ++i)
        cout << motsSansDoublons[i] << ' ' << i+1 << endl;

    //cout << motsSansDoublons[3] << " et " << motsSansDoublons[176] << " : " << (motsSansDoublons[4] == motsSansDoublons[177]) << endl;

    cout << "Nb de mots : " << words.size() << endl;
    cout << "Nb d'entiers : " << entiers.size() << endl;
    //listMotsSansDoublons.unique();

    /*for (set<string>::iterator it = setMotsSansDoublons.begin() ; it != setMotsSansDoublons.end() ; ++it) {
        listMotsSansDoublons.push_back(*it);
        cout << "etape caca" << endl;
    }*/

    /*for (list<string>::iterator it = setMotsSansDoublons.begin() ; it != setMotsSansDoublons.end() ; ++it) {
        motsSansDoublons.push_back(*it);
        cout << "etape caca" << endl;
    }*/

    //entiers.resize(words.size());
    //unsigned i = 1;
    //for (list<string>::iterator it = listMotsSansDoublons.begin() ; it != listMotsSansDoublons.end() ; ++it , ++i/*unsigned i = 0 ; i < words.size() ; ++i*/) {
    for (unsigned i = 0 ; i < words.size() ; ++i) {
        if (words[i] != ";") {
            entiers[i] = find(words[i], motsSansDoublons)+1;
        }
        cout << "Etape 3 : " << i+1 << '/' << words.size() << endl;
        //afficherProgression(i, motsSansDoublons.size());
        //cout << "etape 3" << endl;
    }
    for (unsigned i = 0 ; i < entiers.size() ; ++i) {
        if (entiers[i] == 0) {
            text += '\n';
        } else {
            text += to_string(entiers[i]) + ' ';
        }
        //cout << words[i] << ' ' << entiers[i] << endl;
        //cout << "etape 4" << endl;
    }
    //cout << text;
    return text;
}


int main(int argc, char *argv[]) {
    string text = getFile(argv[1]);
    string dicoPath = argv[1];
    string transPath = argv[1];
    dicoPath = "dico.txt";
    if (transPath.rfind('\\') != string::npos) {
        transPath = "../" + transPath.substr(transPath.rfind('\\')+1, transPath.find(".csv") - transPath.rfind('\\')+1) + ".trans";
    } else if (transPath.rfind('/') != string::npos) {
        transPath = "../" + transPath.substr(transPath.rfind('/')+1, transPath.rfind(".csv") - transPath.rfind('/')+1) + ".trans";
    } else {
        transPath = "../" + transPath.substr(0, transPath.find(".csv")) + ".trans";
    }

    text = traitement();
    createFile(dicoPath, creerDico());
    createFile(transPath, text);
    return 0;
}
