using UnityEngine;
using UnityEngine.Networking;
using System.Collections;
using System.Threading.Tasks;
using System.Text;
using System.IO;
using UnityEditor;
using System;

namespace UnitySource.Assets.Scripts.Communication
{
    public class TestController : MonoBehaviour
    {
        float timePassed = 0f;
        float nextAction = 1f;

        async void Update()
        {
            timePassed += Time.deltaTime;
            if (timePassed > nextAction)
            {
                nextAction += 1f;

                var url = "http://localhost:8088/";
                UnityWebRequest www = UnityWebRequest.Get("http://localhost:8088/posts");

                www.SetRequestHeader("Content-Type", "application/json");
                var operation = www.SendWebRequest();

                while (!operation.isDone)
                    await Task.Yield();

                Debug.Log(www.downloadHandler.text);
            }
        }

        IEnumerator Post(string url, string bodyJsonString)
        {
            UnityWebRequest request = new UnityWebRequest(url, "POST");
            byte[] bodyRaw = Encoding.UTF8.GetBytes(bodyJsonString);
            request.uploadHandler = (UploadHandler)new UploadHandlerRaw(bodyRaw);
            request.downloadHandler = (DownloadHandler)new DownloadHandlerBuffer();
            request.SetRequestHeader("Content-Type", "application/json");
            yield return request.SendWebRequest();
        }

        public static Texture2D LoadPNG(string filePath)
        {

            Texture2D tex = null;
            byte[] fileData;

            if (File.Exists(filePath))
            {
                fileData = File.ReadAllBytes(filePath);
                tex = new Texture2D(2, 2);
                tex.LoadImage(fileData); //..this will auto-resize the texture dimensions.
            }
            return tex;
        }

        public static string Texture2DToBase64(Texture2D texture)
        {
            byte[] imageData = texture.EncodeToPNG();
            return Convert.ToBase64String(imageData);
        }


        async void Start()
        {
            Texture2D imageToSend1 = LoadPNG("Assets/Scripts/Communication/images/rome.jpeg");
            Texture2D imageToSend2 = LoadPNG("Assets/Scripts/Communication/images/indian.jpeg");
            Texture2D imageToSend3 = LoadPNG("Assets/Scripts/Communication/images/knight.jpeg");
            Texture2D imageToSend4 = LoadPNG("Assets/Scripts/Communication/images/prehistoric.jpeg");
            Texture2D imageToSend5 = LoadPNG("Assets/Scripts/Communication/images/napoleon.jpeg");
            Texture2D imageToSend6 = LoadPNG("Assets/Scripts/Communication/images/soldier.jpeg");

            string imageBase1 = Texture2DToBase64(imageToSend1);
            string imageBase2 = Texture2DToBase64(imageToSend2);
            string imageBase3 = Texture2DToBase64(imageToSend3);
            string imageBase4 = Texture2DToBase64(imageToSend4);
            string imageBase5 = Texture2DToBase64(imageToSend5);
            string imageBase6 = Texture2DToBase64(imageToSend6);

            Data item1 = new Data();
            item1.id = 1;
            item1.image = imageBase1;
            item1.name = "Vyzbroj rimskeho vojaka";
            item1.age_category = 1002;
            item1.type_category = 6;
            item1.description = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nullam at arcu a est sollicitudin euismod. Nulla non lectus sed nisl molestie malesuada. Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur? Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vivamus ac leo pretium faucibus. Vestibulum fermentum tortor id mi. Nulla est. Maecenas aliquet accumsan leo. Mauris dolor felis, sagittis at, luctus sed, aliquam non, tellus. Maecenas aliquet accumsan leo. Nullam dapibus fermentum ipsum. Nulla quis diam. Duis viverra diam non justo. Etiam commodo dui eget wisi.";

            Data item2 = new Data();
            item2.id = 2;
            item2.image = imageBase2;
            item2.name = "Obleceni puvodnich indianu";
            item2.age_category = 1005;
            item2.type_category = 6;
            item2.description = "Duis pulvinar. Aliquam ornare wisi eu metus. Aenean fermentum risus id tortor. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos hymenaeos. Phasellus rhoncus. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Phasellus enim erat, vestibulum vel, aliquam a, posuere eu, velit. Sed vel lectus. Donec odio tempus molestie, porttitor ut, iaculis quis, sem. Aenean fermentum risus id tortor. Fusce dui leo, imperdiet in, aliquam sit amet, feugiat eu, orci. Nam sed tellus id magna elementum tincidunt. Aenean id metus id velit ullamcorper pulvinar. Mauris dolor felis, sagittis at, luctus sed, aliquam non, tellus. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Maecenas lorem. Etiam commodo dui eget wisi. Proin pede metus, vulputate nec, fermentum fringilla, vehicula vitae, justo. Praesent in mauris eu tortor porttitor accumsan.";

            Data item3 = new Data();
            item3.id = 3;
            item3.image = imageBase3;
            item3.name = "Rytirska zbroj";
            item3.age_category = 1003;
            item3.type_category = 6;
            item3.description = "Nullam justo enim, consectetuer nec, ullamcorper ac, vestibulum in, elit. Vestibulum erat nulla, ullamcorper nec, rutrum non, nonummy ac, erat. Mauris suscipit, ligula sit amet pharetra semper, nibh ante cursus purus, vel sagittis velit mauris vel metus. Integer vulputate sem a nibh rutrum consequat. Pellentesque pretium lectus id turpis. Donec ipsum massa, ullamcorper in, auctor et, scelerisque sed, est. Vivamus ac leo pretium faucibus. Phasellus rhoncus. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat. Etiam sapien elit, consequat eget, tristique non, venenatis quis, ante.";

            Data item4 = new Data();
            item4.id = 4;
            item4.image = imageBase4;
            item4.name = "Praveka parka z mamuti kuze";
            item4.age_category = 1001;
            item4.type_category = 6;
            item4.description = "Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos hymenaeos. Morbi scelerisque luctus velit. Nullam lectus justo, vulputate eget mollis sed, tempor sed magna. Aenean placerat. Ut tempus purus at lorem. Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. In rutrum. Morbi imperdiet, mauris ac auctor dictum, nisl ligula egestas nulla, et sollicitudin sem purus in lacus. Aliquam erat volutpat. Etiam egestas wisi a erat.";

            Data item5 = new Data();
            item5.id = 5;
            item5.image = imageBase5;
            item5.name = "Napoleon Bonaparte";
            item5.age_category = 1007;
            item5.type_category = 6;
            item5.description = "Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Nullam rhoncus aliquam metus. Phasellus et lorem id felis nonummy placerat. Duis pulvinar. Maecenas lorem. Etiam egestas wisi a erat. Integer pellentesque quam vel velit. Proin mattis lacinia justo. Curabitur vitae diam non enim vestibulum interdum. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Nullam faucibus mi quis velit. Proin in tellus sit amet nibh dignissim sagittis. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Curabitur bibendum justo non orci.";

            Data item6 = new Data();
            item6.id = 6;
            item6.image = imageBase6;
            item6.name = "Vojenska uniforma z prvni svetove valky";
            item6.age_category = 1006;
            item6.type_category = 6;
            item6.description = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nullam at arcu a est sollicitudin euismod. Nulla non lectus sed nisl molestie malesuada. Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur? Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vivamus ac leo pretium faucibus. Vestibulum fermentum tortor id mi. Nulla est. Maecenas aliquet accumsan leo. Mauris dolor felis, sagittis at, luctus sed, aliquam non, tellus. Maecenas aliquet accumsan leo. Nullam dapibus fermentum ipsum. Nulla quis diam. Duis viverra diam non justo. Etiam commodo dui eget wisi.";

            Debug.Log("here");
            Data[] all_data = { item1, item2, item3, item4, item5, item6 };
            Debug.Log(all_data.Length);


            string fullJson = "[";
            int counter = 0;
            foreach (Data each in all_data)
            {
                counter += 1;
                fullJson += JsonUtility.ToJson(each);
                if (counter != 6)
                {
                    fullJson += ",";
                }
            }
            fullJson += "]";

            StartCoroutine(Post("http://localhost:8088/items", fullJson));

            Category c0 = new Category();
            c0.id = 1000;
            c0.name = "All Ages";

            Category c1 = new Category();
            c1.id = 1001;
            c1.name = "Prehistory";

            Category c2 = new Category();
            c2.id = 1002;
            c2.name = "Antient Rome";

            Category c3 = new Category();
            c3.id = 1003;
            c3.name = "Middle Ages";

            Category c4 = new Category();
            c4.id = 1004;
            c4.name = "15th century";

            Category c5 = new Category();
            c5.id = 1005;
            c5.name = "16th century";

            Category c6 = new Category();
            c6.id = 1006;
            c6.name = "Second World War";

            Category c7 = new Category();
            c7.id = 1007;
            c7.name = "Napoleon Wars";

            Category[] age_cats = { c0, c1, c2, c3, c4, c5, c6, c7 };
            string fullJsonCategories = "[";
            int counter2 = 0;
            foreach (Category each in age_cats)
            {
                counter2 += 1;
                fullJsonCategories += JsonUtility.ToJson(each);
                if (counter2 != 8)
                {
                    fullJsonCategories += ",";
                }
            }
            fullJsonCategories += "]";
            StartCoroutine(Post("http://localhost:8088/categories/age", fullJsonCategories));

            Category t0 = new Category();
            t0.id = 0;
            t0.name = "All Types";

            Category t1 = new Category();
            t1.id = 1;
            t1.name = "T-shirts";

            Category t2 = new Category();
            t2.id = 2;
            t2.name = "Shirts";

            Category t3 = new Category();
            t3.id = 3;
            t3.name = "Jackets";

            Category t4 = new Category();
            t4.id = 4;
            t4.name = "Hats";

            Category t5 = new Category();
            t5.id = 5;
            t5.name = "Shoes";

            Category t6 = new Category();
            t6.id = 6;
            t6.name = "Outfits";

            Category[] type_cats = { t0, t1, t2, t3, t4, t5, t6 };
            string fullJsonCategoriesType = "[";
            int counter3 = 0;
            foreach (Category each in type_cats)
            {
                counter3 += 1;
                fullJsonCategoriesType += JsonUtility.ToJson(each);
                if (counter3 != 7)
                {
                    fullJsonCategoriesType += ",";
                }
            }
            fullJsonCategoriesType += "]";
            StartCoroutine(Post("http://localhost:8088/categories/type", fullJsonCategoriesType));

            General g = new General();
            g.age_category_count = 8;
            g.type_category_count = 7;
            g.data_count = 6;

            string generalToSend = JsonUtility.ToJson(g);
            StartCoroutine(Post("http://localhost:8088/general", generalToSend));
        }
    }
}